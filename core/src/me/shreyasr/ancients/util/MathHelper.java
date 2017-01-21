package me.shreyasr.ancients.util;

public class MathHelper {

    public static float clamp(float lo, float val, float hi) {
        return (lo<=val)?((val<=hi)?val:((lo<hi)?hi:lo)):((lo<=hi)?lo:((val<hi)?hi:val));
    }
}
