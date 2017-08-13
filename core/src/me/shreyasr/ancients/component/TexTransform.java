package me.shreyasr.ancients.component;

import com.badlogic.gdx.graphics.Color;
import com.esotericsoftware.kryo.DefaultSerializer;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import lombok.ToString;

@ToString
@DefaultSerializer(TexTransform.TexTransformSerializer.class)
public class TexTransform {
    
    public static class TexTransformSerializer extends Serializer<TexTransform> {
    
        @Override
        public void write(Kryo kryo, Output output, TexTransform ttc) {
            output.writeInt(ttc.srcWidth, true);
            output.writeInt(ttc.srcHeight, true);
            output.writeInt(ttc.screenWidth / ttc.srcWidth, true);
            output.writeFloat(ttc.rotation);
            output.writeBoolean(ttc.hide);
        }
    
        @Override
        public TexTransform read(Kryo kryo, Input input, Class<TexTransform> type) {
            int width = input.readInt(true);
            int height = input.readInt(true);
            int scale = input.readInt(true);
            
            TexTransform ttc = new TexTransform(width, height, scale);
            ttc.rotation = input.readFloat();
            ttc.hide = input.readBoolean();
            return ttc;
        }
    }

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
    
    protected TexTransform() { }
    
    public TexTransform(int screenWidth, int screenHeight, int originX, int originY,
                        int srcX, int srcY, int srcWidth, int srcHeight,
                        float rotation, boolean hide, Color color) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.originX = originX;
        this.originY = originY;
        this.srcX = srcX;
        this.srcY = srcY;
        this.srcWidth = srcWidth;
        this.srcHeight = srcHeight;
        this.rotation = rotation;
        this.hide = hide;
        this.color = color;
    }
    
    public TexTransform(int width, int height, int scale) {
        this(width*scale, height*scale, (width*scale)/2, (height*scale)/2,
                0, 0, width, height, 0, false, Color.WHITE);
    }
    
    public TexTransform(TexTransform ttc) {
        this(ttc.screenWidth, ttc.screenHeight, ttc.originX, ttc.originY,
                ttc.srcX, ttc.srcY, ttc.srcWidth, ttc.srcHeight, ttc.rotation, ttc.hide, ttc.color);
    }
}
