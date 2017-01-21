package me.shreyasr.ancients.component;

import com.badlogic.ashley.core.Component;

public class PosComponent implements Component {

    public float x;
    public float y;

    public PosComponent(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
