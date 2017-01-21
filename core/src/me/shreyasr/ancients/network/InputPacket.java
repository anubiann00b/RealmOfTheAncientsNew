package me.shreyasr.ancients.network;

import me.shreyasr.ancients.util.InputState;

public class InputPacket {

    public final InputState w;
    public final InputState a;
    public final InputState s;
    public final InputState d;

    public InputPacket(InputState w, InputState a, InputState s, InputState d) {
        this.w = w;
        this.a = a;
        this.s = s;
        this.d = d;
    }

    @Override
    public String toString() {
        return "InputPacket{" +
                "w=" + w +
                ", a=" + a +
                ", s=" + s +
                ", d=" + d +
                '}';
    }
}
