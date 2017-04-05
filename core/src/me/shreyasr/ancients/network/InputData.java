package me.shreyasr.ancients.network;

import me.shreyasr.ancients.component.Pos;

public class InputData {

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
