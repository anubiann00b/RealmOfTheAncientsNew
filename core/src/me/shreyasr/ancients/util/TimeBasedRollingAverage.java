package me.shreyasr.ancients.util;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Stores samples with their times and gets a rolling average over a period of time.
 */
public class TimeBasedRollingAverage {
    
    private final int millis;
    private final SortedSet<Sample> samples = new TreeSet<>();
    
    public TimeBasedRollingAverage(int millis) {
        this.millis = millis;
    }
    
    /**
     * Discards samples older than the time period and returned the sum of the values remaining.
     *
     * @return The sum of the values over the last time period.
     */
    public int get() {
        int accumulator = 0;
        
        synchronized (samples) {
            samples.headSet(new Sample(System.currentTimeMillis() - millis, 0)).clear();
    
            for (Sample sample : samples) {
                accumulator += sample.val;
            }
        }
        
        return accumulator;
    }
    
    /**
     * Puts a new sample in at the current time.
     *
     * @param value The value to put in.
     */
    public void put(int value) {
        synchronized (samples) {
            samples.add(new Sample(System.currentTimeMillis(), value));
        }
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
