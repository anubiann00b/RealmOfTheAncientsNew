package me.shreyasr.ancients.component;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import lombok.ToString;

@ToString
public class Pos implements KryoSerializable {
    
    @Override
    public void write(Kryo kryo, Output output) {
        output.writeFloat(x, 1000, true);
        output.writeFloat(y, 1000, true);
    }
    
    @Override
    public void read(Kryo kryo, Input input) {
        x = input.readFloat(1000, true);
        y = input.readFloat(1000, true);
    }
    
    public float x;
    public float y;
    
    protected Pos() { }
    
    public Pos(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    public Pos(Pos other) {
        this(other.x, other.y);
    }
    
    public void set(Pos other) {
        this.x = other.x;
        this.y = other.y;
    }
    
    public Pos sub(Pos other) {
        return new Pos(x-other.x, y-other.y);
    }
    
    public float getDirDegrees() {
        return (float) Math.toDegrees(getDirRadians());
    }
    
    public float getDirRadians() {
        return (float) Math.atan2(y, x);
    }
    
    private float magnitude() {
        return (float) Math.sqrt(x*x + y*y);
    }
    
    public float distanceTo(Pos other) {
        return (float) Math.sqrt(distanceSquaredTo(other));
    }
    
    public float distanceSquaredTo(Pos other) {
        return (other.x-this.x)*(other.x-this.x) + (other.y-this.y)*(other.y-this.y);
    }
    
    public Pos normalize() {
        x /= magnitude();
        y /= magnitude();
        return this;
    }
}
