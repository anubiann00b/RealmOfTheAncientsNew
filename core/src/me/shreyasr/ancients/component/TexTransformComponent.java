package me.shreyasr.ancients.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;

public class TexTransformComponent implements Component {

    public int screenWidth;
    public int screenHeight;

    public int originX;
    public int originY;

    public int srcX;
    public int srcY;
    public int srcWidth;
    public int srcHeight;

    public float rotation;

    public boolean hide;

    public Color color;
    
    protected TexTransformComponent() { }

    public TexTransformComponent(int width, int height) {
        this.screenWidth = width;
        this.screenHeight = height;
        this.originX = this.screenWidth/2;
        this.originY = this.screenHeight/2;
        this.srcX = 0;
        this.srcY = 0;
        this.srcWidth = width;
        this.srcHeight = height;
        this.rotation = 0;
        this.hide = false;
        this.color = Color.WHITE;
    }
}
