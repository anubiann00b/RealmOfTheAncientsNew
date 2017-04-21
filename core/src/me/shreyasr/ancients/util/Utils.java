package me.shreyasr.ancients.util;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.esotericsoftware.minlog.Log;

import java.util.Locale;

public class Utils {
    
    public static Datafile readDataFile(FileHandle file) {
        Datafile datafile = new Datafile();
    
        String data = file.readString();
        for (String line : data.split("\r\n")) {
            if (line.charAt(0) == ';') continue;
            
            int colonPos = line.indexOf('=');
            if (colonPos == -1) {
                Log.warn("datafile", "Line malformed: " + line);
                continue;
            }
            
            String key = line.substring(0, colonPos);
            String valueString = line.substring(colonPos+1);
            
            try {
                int valueInt = Integer.valueOf(valueString);
                datafile.putInt(key, valueInt);
            } catch (NumberFormatException ignored) {
                try {
                    float valueFloat = Float.valueOf(valueString);
                    datafile.putFloat(key, valueFloat);
                } catch (NumberFormatException ignored2) {
                    datafile.putString(key, valueString);
                }
            }
        }
        
        return datafile;
    }
    
    /**
     * Draws an arc of a circle on the ShapeRenderer as a polyline.
     *
     * @param shape The ShapeRenderer to draw on.
     * @param x The x coordinate of the center of the circle.
     * @param y The y coordinate of the center of the circle.
     * @param radius The radius of the circle.
     * @param angleStart The starting angle of the arc.
     * @param angleWidth The width of the arc in degrees counter-clockwise.
     */
    public static void arcCurve(ShapeRenderer shape, float x, float y, float radius, float angleStart, float angleWidth) {
        int segments = 10;
        float[] vertices = new float[segments*2+2];
    
        for (int i = 0; i <= segments; i++) {
            float angle = angleStart + i*angleWidth/segments;
            vertices[2*i] = (float) (x + radius*Math.cos(Math.toRadians(angle)));
            vertices[2*i+1] = (float) (y + radius*Math.sin(Math.toRadians(angle)));
        }
        
        shape.polyline(vertices);
    }

    public static boolean angularContains(float angle, float start, float width) {
        angle = normalizeAngle(angle);
        start = normalizeAngle(start);
        width = normalizeAngle(width);
        
        // If the angle is less than the start, we're seeing if the range wraps around back to it
        // eg, angle=10, start=350, width=30
        // angle += 360 -> angle=370, can use simple range math
        if (angle < start) {
            angle += 360;
        }
        
        return angle >= start
                && angle <= start + width;
    }
    
    /**
     * @param angle An angle in degrees.
     * @return The angle, between 0 and 360.
     */
    public static float normalizeAngle(float angle) {
        return positiveMod(angle, 360);
    }
    
    /**
     * Returns the mathematical value of value % mod, instead of the remainder
     *
     * Java behavior (remainder): -5 % 10 -> -5 and -16 % 10 -> -6
     * Modulus behavior (this method): -5 % 10 -> 5 and -16 % 10 -> 4
     */
    public static float positiveMod(float value, float mod) {
        return (value % mod + mod) % mod;
    }
    
    // http://stackoverflow.com/a/3758880/2197700
    public static String humanReadableBitCount(long bits) {
        if (bits < 1000) return bits + " b/s";
        int exp = (int) (Math.log(bits) / Math.log(1000));
        char pre = "KMGTPE".charAt(exp-1);
        return String.format(Locale.US, "%.1f %sb/s", bits / Math.pow(1000, exp), pre);
    }
    
    public static float clamp(float lo, float val, float hi) {
        return (lo<=val)?((val<=hi)?val:((lo<hi)?hi:lo)):((lo<=hi)?lo:((val<hi)?hi:val));
    }
}
