package me.shreyasr.ancients.util;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.ByteBufferOutput;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.KryoSerialization;
import com.esotericsoftware.minlog.Log;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class InterceptingKryoSerialization extends KryoSerialization {
    
    private final Kryo kryoRef;
    private final ByteBufferOutput countingOutput = new ByteBufferOutput();
    
    private TimeBasedRollingAverage readByteTimeAverage = new TimeBasedRollingAverage(1000);
    private TimeBasedRollingAverage writtenByteTimeAverage = new TimeBasedRollingAverage(1000);
    
    public int getReadByteCount() { return readByteTimeAverage.get(); }
    public int getWrittenByteCount() { return writtenByteTimeAverage.get(); }
    
    /**
     * Do the same thing `new KryoSerialization()` does, then pass the Kryo object to our constructor to grab a reference
     */
    public InterceptingKryoSerialization() {
        this(new Kryo());
        kryoRef.setReferences(false);
        kryoRef.setRegistrationRequired(true);
    }
    
    /**
     * Defer to parent, but grab a reference to the private kryo object
     */
    public InterceptingKryoSerialization(Kryo kryo) {
        super(kryo);
        kryoRef = kryo;
    }
    
    public synchronized void write (Connection connection, ByteBuffer buffer, Object object) {
        ByteBuffer countingBuffer = buffer.duplicate();
        countingOutput.setBuffer(countingBuffer);
    
        kryoRef.writeClassAndObject(countingOutput, object);
        
        int byteCount = countingBuffer.position();
        byte[] bytesWritten = new byte[byteCount];
        countingBuffer.position(0);
        countingBuffer.get(bytesWritten, 0, byteCount);
    
        Log.debug("kryointerceptor", "Wrote bytes: " + byteCount + ", " + object
                + " as " + Arrays.toString(bytesWritten).replace(", ", " "));
    
        writtenByteTimeAverage.put(byteCount);
        
        countingOutput.flush();
        
        super.write(connection, buffer, object);
    }
    
    @Override
    public synchronized Object read(Connection connection, ByteBuffer buffer) {
        try {
            Object obj = super.read(connection, buffer);
        
            ByteBuffer countingBuffer = buffer.duplicate(); // Makes duplicate backed by same data with it's own position
            int byteCount = countingBuffer.position(); // Current position is at the end of the data
            byte[] bytesWritten = new byte[byteCount];
            countingBuffer.position(0); // Reset the position so we read from the beginning
            countingBuffer.get(bytesWritten, 0, byteCount); // Reads byteCount bytes into bytesWritten
        
            Log.debug("kryointerceptor", "Read bytes: " + byteCount + ", " + obj
                    + " as " + Arrays.toString(bytesWritten).replace(", ", " "));
        
            readByteTimeAverage.put(byteCount);
            
            return obj;
        } catch (KryoException e) {
            Log.error("kryo", "Error, discarding packet: " + e);
            e.printStackTrace();
            buffer.position(buffer.limit());
        }
        return null;
    }
}
