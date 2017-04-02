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
    
    @Override
    public String toString() {
        return "Pos{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
