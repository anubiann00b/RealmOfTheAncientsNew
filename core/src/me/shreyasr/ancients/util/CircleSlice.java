package me.shreyasr.ancients.util;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.esotericsoftware.kryo.DefaultSerializer;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import lombok.ToString;
import me.shreyasr.ancients.component.Pos;

import java.util.stream.Stream;

@ToString
@DefaultSerializer(CircleSlice.CircleSliceSerializer.class)
public class CircleSlice {
    
    public static class CircleSliceSerializer extends Serializer<CircleSlice> {
    
        @Override
        public void write(Kryo kryo, Output output, CircleSlice cs) {
            output.writeInt(cs.minRadius, true);
            output.writeInt(cs.maxRadius, true);
            output.writeInt(cs.angleWidth, true);
            output.writeFloat(cs.angleStart, 8, true);
        }
    
        @Override
        public CircleSlice read(Kryo kryo, Input input, Class<CircleSlice> type) {
            int minRadius = input.readInt(true);
            int maxRadius = input.readInt(true);
            int angleWidth = input.readInt(true);
            float angleStart = input.readFloat(8, true);
            return new CircleSlice(minRadius, maxRadius, angleWidth, angleStart);
        }
    }
    
    private float angleStart;
    
    public final int minRadius;
    public final int maxRadius;
    public final int angleWidth;
    
    public CircleSlice(int minRadius, int maxRadius, int angleWidth) {
        this(minRadius, maxRadius, angleWidth, 0);
    }
    
    public CircleSlice(int minRadius, int maxRadius, int angleWidth, float angleStart) {
        this.minRadius = minRadius;
        this.maxRadius = maxRadius;
        this.angleWidth = angleWidth;
        this.angleStart = angleStart;
    }
    
    public CircleSlice(CircleSlice cs) {
        this(cs.minRadius, cs.maxRadius, cs.angleWidth, cs.angleStart);
    }
    
    public void setAngle(float angle) {
        float newAngleStart = angle - angleWidth/2;
        angleStart = (newAngleStart+360)%360;
    }
    
    /**
     * Calculates if this CircleSlice intersects a Rectangle by sampling points on the border of the rectangle and seeing
     * if they're inside of the CircleSlice. Good enough for most use cases.
     *
     * @param pos The position of this CircleSlice.
     * @return If the Rectangle overlaps this CircleSlice.
     */
    public boolean overlaps(Pos pos, Rectangle rectangle) {
        return Stream.of(
                // Points on the rectangle
                new Pos(rectangle.x, rectangle.y),
                new Pos(rectangle.x+rectangle.width/3f, rectangle.y),
                new Pos(rectangle.x+rectangle.width/3f*2, rectangle.y),
                new Pos(rectangle.x+rectangle.width, rectangle.y),
                
                new Pos(rectangle.x, rectangle.y+rectangle.height),
                new Pos(rectangle.x+rectangle.width/3f, rectangle.y+rectangle.height),
                new Pos(rectangle.x+rectangle.width/3f*2, rectangle.y+rectangle.height),
                new Pos(rectangle.x+rectangle.width, rectangle.y+rectangle.height),
                
                new Pos(rectangle.x, rectangle.y),
                new Pos(rectangle.x, rectangle.y+rectangle.height/3f),
                new Pos(rectangle.x, rectangle.y+rectangle.height/3f*2),
                new Pos(rectangle.x, rectangle.y+rectangle.height),
                
                new Pos(rectangle.x+rectangle.width, rectangle.y),
                new Pos(rectangle.x+rectangle.width, rectangle.y+rectangle.height/3f),
                new Pos(rectangle.x+rectangle.width, rectangle.y+rectangle.height/3f*2),
                new Pos(rectangle.x+rectangle.width, rectangle.y+rectangle.height)
        ).anyMatch(rectPointPos -> {
            // See if point is in distance range
            float distanceSquared = pos.distanceSquaredTo(rectPointPos);
            if (distanceSquared <= minRadius*minRadius || distanceSquared >= maxRadius*maxRadius) {
                return false;
            }
            
            // See if point is in angle range
            float angle = rectPointPos.sub(pos).getDirDegrees();
            return Utils.angularContains(angle, angleStart, angleWidth);
        });
    }
    
    /**
     * Draws this CircleSlice, as two lines and two arcs.
     *
     * @param shape The ShapeRenderer to draw on.
     * @param pos The position to draw at.
     */
    public void draw(ShapeRenderer shape, Pos pos) {
        Utils.arcCurve(shape, pos.x, pos.y, minRadius, angleStart, angleWidth);
        Utils.arcCurve(shape, pos.x, pos.y, maxRadius, angleStart, angleWidth);
        shape.line(
                pos.x + (float) Math.cos(Math.toRadians(angleStart)) * minRadius,
                pos.y + (float) Math.sin(Math.toRadians(angleStart)) * minRadius,
                pos.x + (float) Math.cos(Math.toRadians(angleStart)) * maxRadius,
                pos.y + (float) Math.sin(Math.toRadians(angleStart)) * maxRadius);
        shape.line(
                pos.x + (float) Math.cos(Math.toRadians(angleStart+angleWidth)) * minRadius,
                pos.y + (float) Math.sin(Math.toRadians(angleStart+angleWidth)) * minRadius,
                pos.x + (float) Math.cos(Math.toRadians(angleStart+angleWidth)) * maxRadius,
                pos.y + (float) Math.sin(Math.toRadians(angleStart+angleWidth)) * maxRadius);
    }
}
