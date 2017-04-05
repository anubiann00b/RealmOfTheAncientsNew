package me.shreyasr.ancients.util;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.ByteBufferOutput;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.KryoSerialization;
import com.esotericsoftware.minlog.Log;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.TreeMap;

public class InterceptingKryoSerialization extends KryoSerialization {
    
    private final Kryo kryoRef;
    private final ByteBufferOutput countingOutput = new ByteBufferOutput();
    
    TreeMap<Long, Integer> readByteCountSamples = new TreeMap<>();
    
    private TimeBasedRollingAverage readByteTimeAverage = new TimeBasedRollingAverage(1000);
    private TimeBasedRollingAverage writtenByteTimeAverage = new TimeBasedRollingAverage(1000);
    
    public int getReadByteCount() {
        return readByteTimeAverage.get();
    }
    
    public int getWrittenByteCount() {
        return writtenByteTimeAverage.get();
    }
    
    public InterceptingKryoSerialization() {
        this(new Kryo());
        kryoRef.setReferences(false);
        kryoRef.setRegistrationRequired(true);
    }
    
    public InterceptingKryoSerialization(Kryo kryo) {
        super(kryo);
        kryoRef = kryo;
    }
    
    public synchronized void write (Connection connection, ByteBuffer buffer, Object object) {
        ByteBuffer countingBuffer = buffer.duplicate();
        countingOutput.setBuffer(countingBuffer);
    
//        kryoRef.getContext().put("connection", connection);
        kryoRef.writeClassAndObject(countingOutput, object);
        
        int byteCount = countingBuffer.position();
        byte[] bytesWritten = new byte[byteCount];
        countingBuffer.position(0);
        countingBuffer.get(bytesWritten, 0, byteCount);
    
        Log.trace("kryointerceptor", "Wrote bytes: " + byteCount + ", " + object
                + " as " + Arrays.toString(bytesWritten).replace(", ", " "));
    
        writtenByteTimeAverage.put(byteCount);
        
        countingOutput.flush();
        
        super.write(connection, buffer, object);
    }
    
    @Override
    public synchronized Object read(Connection connection, ByteBuffer buffer) {
        Object obj = super.read(connection, buffer);
    
        ByteBuffer countingBuffer = buffer.duplicate();
        int byteCount = countingBuffer.position();
        byte[] bytesWritten = new byte[byteCount];
        countingBuffer.position(0);
        countingBuffer.get(bytesWritten, 0, byteCount);
    
        Log.trace("kryointerceptor", "Read bytes: " + byteCount + ", " + obj
                + " as " + Arrays.toString(bytesWritten).replace(", ", " "));
    
        readByteTimeAverage.put(byteCount);
        
        return obj;
    }
}
