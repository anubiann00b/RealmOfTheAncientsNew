package me.shreyasr.ancients.component;

public class Pos {
    
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
    
    public float getDirDegrees() {
        return (float) Math.toDegrees(Math.atan2(y, x));
    }
    
    public float distanceTo(Pos other) {
        return (float) Math.sqrt(distanceSquaredTo(other));
    }
    
    public float distanceSquaredTo(Pos other) {
        return (other.x-this.x)*(other.x-this.x) + (other.y-this.y)*(other.y-this.y);
    }
    
    public Pos sub(Pos other) {
        return new Pos(x-other.x, y-other.y);
    }
    
    @Override
    public String toString() {
        return "Pos{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
