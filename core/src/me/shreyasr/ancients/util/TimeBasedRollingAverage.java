package me.shreyasr.ancients.util;

import java.util.SortedSet;
import java.util.TreeSet;

public class TimeBasedRollingAverage {
    
    private final int millis;
    private final SortedSet<Sample> samples = new TreeSet<>();
    
    public TimeBasedRollingAverage(int millis) {
        this.millis = millis;
    }
    
    public int get() {
        samples.headSet(new Sample(System.currentTimeMillis()-millis, 0)).clear();
        
        int accumulator = 0;
        for (Sample sample : samples) {
            accumulator += sample.val;
        }
        return accumulator;
    }
    
    public void put(int value) {
        samples.add(new Sample(System.currentTimeMillis(), value));
    }
    
    private class Sample implements Comparable<Sample> {
    
        private final long time;
        private final int val;
    
        private Sample(long time, int val) {
            this.time = time;
            this.val = val;
        }
    
        @Override
        public int compareTo(Sample o) {
            return Long.compare(time, o.time);
        }
    }
}
