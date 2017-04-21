package me.shreyasr.ancients.util;

import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString
public class Datafile {
    
    private final Map<String, String> strings = new HashMap<>();
    private final Map<String, Integer> ints = new HashMap<>();
    private final Map<String, Float> floats = new HashMap<>();
    
    public void putInt(String name, int value) {
        ints.put(name, value);
    }
    
    public void putFloat(String name, float value) {
        floats.put(name, value);
    }
    
    public void putString(String name, String value) {
        strings.put(name, value);
    }
    
    public int getInt(String name) {
        if (!ints.containsKey(name)) {
            throw new RuntimeException("Datafile does not have int parameter: " + name + ", " + this);
        }
        return ints.get(name);
    }
    
    public float getFloat(String name) {
        if (!floats.containsKey(name)) {
            throw new RuntimeException("Datafile does not have float parameter: " + name + ", " + this);
        }
        return floats.get(name);
    }
    
    public String getString(String name) {
        if (!strings.containsKey(name)) {
            throw new RuntimeException("Datafile does not have String parameter: " + name + ", " + this);
        }
        return strings.get(name);
    }
    
//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder("Datafile{");
//
//        strings.entrySet().forEach(entry -> sb.append(entry.getKey()).append(":").append(entry.getValue()).append(", "));
//        ints.entrySet().forEach(entry -> sb.append(entry.getKey()).append(":").append(entry.getValue()).append(", "));
//        floats.entrySet().forEach(entry -> sb.append(entry.getKey()).append(":").append(entry.getValue()).append(", "));
//
//        return sb.append("}").toString();
//    }
}
