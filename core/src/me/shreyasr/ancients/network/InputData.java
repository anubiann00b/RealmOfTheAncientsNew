package me.shreyasr.ancients.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import me.shreyasr.ancients.component.Pos;

import java.util.BitSet;

public class InputData implements KryoSerializable {
    
    @Override
    public void write(Kryo kryo, Output output) {
        BitSet bits = new BitSet(8);
        bits.set(0, w);
        bits.set(1, a);
        bits.set(2, s);
        bits.set(3, d);
        bits.set(4, leftMouse);
        bits.set(5, rightMouse);
        output.writeByte(bits.isEmpty() ? 0 : bits.toByteArray()[0]);
        kryo.writeObject(output, pos);
    }
    
    @Override
    public void read(Kryo kryo, Input input) {
        byte dataByte = input.readByte();
        BitSet bits = BitSet.valueOf(new byte[] { dataByte });
        w = bits.get(0);
        a = bits.get(1);
        s = bits.get(2);
        d = bits.get(3);
        leftMouse = bits.get(4);
        rightMouse = bits.get(5);
        pos = kryo.readObject(input, Pos.class);
        System.out.println(this);
    }

    public boolean w;
    public boolean a;
    public boolean s;
    public boolean d;
    public boolean leftMouse;
    public boolean rightMouse;
    public Pos pos;
    
    protected InputData() {

    }

    public InputData(boolean w, boolean a, boolean s, boolean d, Pos pos, boolean leftMouse, boolean rightMouse) {
        this.w = w;
        this.a = a;
        this.s = s;
        this.d = d;
        this.pos = pos;
        this.leftMouse = leftMouse;
        this.rightMouse = rightMouse;
    }
    
    @Override
    public String toString() {
        return "InputData{" +
                "w=" + w +
                ", a=" + a +
                ", s=" + s +
                ", d=" + d +
                ", leftMouse=" + leftMouse +
                ", rightMouse=" + rightMouse +
                ", pos=" + pos +
                '}';
    }
}
