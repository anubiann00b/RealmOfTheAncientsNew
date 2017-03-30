package me.shreyasr.ancients.network;


public class InputData {

    public boolean w;
    public boolean a;
    public boolean s;
    public boolean d;

    protected InputData() {

    }

    public InputData(boolean w, boolean a, boolean s, boolean d) {
        this.w = w;
        this.a = a;
        this.s = s;
        this.d = d;
    }

    @Override
    public String toString() {
        return "InputData{" +
                "w=" + w +
                ", a=" + a +
                ", s=" + s +
                ", d=" + d +
                '}';
    }
}
