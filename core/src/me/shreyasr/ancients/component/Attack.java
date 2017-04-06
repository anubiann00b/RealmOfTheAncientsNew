package me.shreyasr.ancients.component;

import lombok.ToString;
import me.shreyasr.ancients.component.attack.WeaponAnimation;
import me.shreyasr.ancients.network.InputData;
import me.shreyasr.ancients.util.CircleSlice;

public abstract class Attack {
    
    public abstract void update(int deltaMillis, Pos pos, InputData input, WeaponHitbox weaponHitbox);
    public abstract Attack copy();
    public abstract void applyFrame(WeaponHitbox weaponHitbox);
    public abstract AnimFrame getCurrentAnimFrame();
    
    @ToString
    public static class AnimFrame {
        
        public final WeaponAnimation animation;
        public final int frameNumber;
        public final int duration;
        public final CircleSlice hitbox;
        public final float hitboxAngle;
        
        protected AnimFrame() {
            this(null, 0, 0, null, 0);
        }
        
        public AnimFrame(WeaponAnimation animation, int frameNumber, int duration, CircleSlice hitbox, float hitboxAngle) {
            this.animation = animation;
            this.frameNumber = frameNumber;
            this.duration = duration;
            this.hitbox = hitbox;
            this.hitboxAngle = hitboxAngle;
        }
    }
}
