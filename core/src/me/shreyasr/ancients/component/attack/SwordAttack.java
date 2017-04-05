package me.shreyasr.ancients.component.attack;

import me.shreyasr.ancients.component.Attack;
import me.shreyasr.ancients.component.Pos;
import me.shreyasr.ancients.component.WeaponHitbox;
import me.shreyasr.ancients.network.InputData;

import java.util.stream.Stream;

public class SwordAttack extends Attack {
    
    private AttackDirections attackDirections;
    private AnimFrame[] frames = new AnimFrame[0];
    private int attackTimer = -1; // -1 denotes not attacking
    
    private SwordAttack() {
        this(null);
    }
    
    public SwordAttack(AttackDirections attackDirections) {
        this.attackDirections = attackDirections;
    }
    
    @Override
    public void update(int deltaMillis, Pos pos, InputData input, WeaponHitbox weaponHitbox) {
        if (attackTimer == -1 && input.leftMouse) {
            attackTimer = 0;
            if (input.pos != null) {
                frames = attackDirections.getFramesForMousePos(input.pos.sub(pos));
            } else {
                frames = new AnimFrame[0];
            }
        }
        
        if (attackTimer != -1) {
            attackTimer += deltaMillis;
        }
    
        int totalAnimDuration = Stream.of(frames).mapToInt(frame -> frame.duration).sum();
        if (attackTimer > totalAnimDuration) {
            attackTimer = -1;
        }
        
        applyFrame(weaponHitbox);
    }
    
    public int getCurrentFrame() {
        if (attackTimer > -1) {
            int timeUpToEndOfFrame = 0;
            for (int i = 0; i < frames.length; i++) {
                timeUpToEndOfFrame += frames[i].duration;
        
                // The time up to the end of the frame will be greater than the attack timer
                // on the first frame that hasn't ended before the current timer
                if (timeUpToEndOfFrame >= attackTimer) {
                    return i;
                }
            }
            throw new RuntimeException("Error in SwordAttack, " + this);
        } else {
            return -1;
        }
    }
    
    public AnimFrame getCurrentAnimFrame() {
        int currentFrame = getCurrentFrame();
        return currentFrame == -1 ? null : frames[currentFrame];
    }
    
    public void applyFrame(WeaponHitbox weaponHitbox) {
        AnimFrame frame = getCurrentAnimFrame();
        
        if (frame == null || frame.frameNumber == -1) {
            weaponHitbox.active = false;
        } else {
            weaponHitbox.active = true;
            weaponHitbox.setCs(frame.hitbox);
            weaponHitbox.setAngle(frame.hitboxAngle);
        }
    }
    
    @Override
    public SwordAttack copy() {
        SwordAttack attack = new SwordAttack(attackDirections);
        attack.attackTimer = attackTimer;
        attack.frames = frames;
        return attack;
    }
}
