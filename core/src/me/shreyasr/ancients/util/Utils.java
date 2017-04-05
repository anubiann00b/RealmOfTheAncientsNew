package me.shreyasr.ancients.util;

public class Utils {

    public static float clamp(float lo, float val, float hi) {
        return (lo<=val)?((val<=hi)?val:((lo<hi)?hi:lo)):((lo<=hi)?lo:((val<hi)?hi:val));
    }
    
    // http://stackoverflow.com/a/3758880/2197700
    public static String humanReadableByteCount(long bytes) {
        if (bytes < 1000) return bytes + " b/s";
        int exp = (int) (Math.log(bytes) / Math.log(1000));
        char pre = "KMGTPE".charAt(exp-1);
        return String.format("%.1f %sb/s", bytes / Math.pow(1000, exp), pre);
    }
}
