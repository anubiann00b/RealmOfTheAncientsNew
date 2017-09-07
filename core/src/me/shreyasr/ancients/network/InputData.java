package me.shreyasr.ancients.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import lombok.ToString;
import me.shreyasr.ancients.component.Pos;

import java.util.BitSet;

@ToString
public class InputData implements KryoSerializable {
    
    @Override
    public void write(Kryo kryo, Output output) {
        BitSet bits = new BitSet(8);
        bits.set(0, moveUp);
        bits.set(1, moveLeft);
        bits.set(2, moveDown);
        bits.set(3, moveRight);
        bits.set(4, attack);
        bits.set(5, dash);
        bits.set(6, switchWeapons);
        output.writeByte(bits.isEmpty() ? 0 : bits.toByteArray()[0]);
        kryo.writeObjectOrNull(output, mousePos, Pos.class);
    }
    
    @Override
    public void read(Kryo kryo, Input input) {
        byte dataByte = input.readByte();
        BitSet bits = BitSet.valueOf(new byte[] { dataByte });
        moveUp = bits.get(0);
        moveLeft = bits.get(1);
        moveDown = bits.get(2);
        moveRight = bits.get(3);
        attack = bits.get(4);
        dash = bits.get(5);
        switchWeapons = bits.get(6);
        mousePos = kryo.readObjectOrNull(input, Pos.class);
    }

    /** The mouse position in the world, not in the window */
    public Pos mousePos;
    public boolean moveUp;
    public boolean moveLeft;
    public boolean moveDown;
    public boolean moveRight;
    public boolean attack;
    public boolean dash;
    public boolean switchWeapons;
    
    public InputData(Pos mousePos, boolean moveUp, boolean moveLeft, boolean moveDown, boolean moveRight,
                     boolean attack, boolean dash, boolean switchWeapons) {
        this.mousePos = mousePos;
        this.moveUp = moveUp;
        this.moveLeft = moveLeft;
        this.moveDown = moveDown;
        this.moveRight = moveRight;
        this.attack = attack;
        this.dash = dash;
        this.switchWeapons = switchWeapons;
    }
}
