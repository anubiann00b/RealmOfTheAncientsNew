package me.shreyasr.ancients.component;

import lombok.ToString;
import me.shreyasr.ancients.component.attack.WeaponAnimation;
import me.shreyasr.ancients.game.PlayerData;
import me.shreyasr.ancients.network.InputData;
import me.shreyasr.ancients.util.CircleSlice;

public abstract class Attack {
    
    public abstract Attack copy();
    public abstract boolean isAttacking();
    public abstract void update(PlayerData playerData, int deltaMillis, Pos pos, InputData input, WeaponHitbox weaponHitbox);
    public abstract void applyFrame(PlayerData playerData, WeaponHitbox weaponHitbox);
    public abstract AnimFrame getCurrentAnimFrame(PlayerData playerData);
    
    @ToString
    public static class AnimFrame {
        
        public final WeaponAnimation animation;
        public final int frameNumber;
        public final int duration;
        public final CircleSlice hitbox;
        public final float hitboxAngle;
        
        public AnimFrame(WeaponAnimation animation, int frameNumber, int duration, CircleSlice hitbox, float hitboxAngle) {
            this.animation = animation;
            this.frameNumber = frameNumber;
            this.duration = duration;
            this.hitbox = hitbox;
            this.hitboxAngle = hitboxAngle;
        }
    }
}
