package me.shreyasr.ancients.component.attack;

import lombok.ToString;
import me.shreyasr.ancients.component.Attack;
import me.shreyasr.ancients.component.Pos;
import me.shreyasr.ancients.component.WeaponHitbox;
import me.shreyasr.ancients.game.PlayerData;
import me.shreyasr.ancients.network.InputData;

import java.util.stream.Stream;

@ToString
public class SwordAttack extends Attack {
    
    @Override
    public SwordAttack copy() {
        SwordAttack attack = new SwordAttack();
        attack.attackTimer = attackTimer;
        attack.directionIndex = directionIndex;
        attack.currentWeaponIndex = currentWeaponIndex;
        return attack;
    }
    
    private int directionIndex = -1;
    public int attackTimer = -1; // -1 denotes not attacking
    public int currentWeaponIndex = 0;
    
    @Override
    public boolean isAttacking() {
        return attackTimer != -1;
    }
    
    @Override
    public void update(PlayerData playerData, int deltaMillis, Pos pos, InputData input, WeaponHitbox weaponHitbox) {
        if (attackTimer == -1 && input.attack) {
            attackTimer = 0;
            if (input.mousePos != null) {
                directionIndex = getCurrentWeapon(playerData).getDirectionIndexForMousePos(input.mousePos.sub(pos));
            } else {
                directionIndex = -1;
            }
        }
        
        if (attackTimer != -1) {
            attackTimer += deltaMillis;
        }
    
        int totalAnimDuration = Stream.of(getFrames(playerData)).mapToInt(frame -> frame.duration).sum();
        if (attackTimer > totalAnimDuration) {
            attackTimer = -1;
        }
        
        applyFrame(playerData, weaponHitbox);
    }
    
    @Override
    public AnimFrame getCurrentAnimFrame(PlayerData playerData) {
        AnimFrame[] frames = getFrames(playerData);
        int currentFrame = getCurrentFrame(frames);
        return currentFrame == -1 ? null : frames[currentFrame];
    }
    
    @Override
    public void applyFrame(PlayerData playerData, WeaponHitbox weaponHitbox) {
        AnimFrame frame = getCurrentAnimFrame(playerData);
        
        if (frame == null || frame.frameNumber == -1) {
            weaponHitbox.active = false;
        } else {
            weaponHitbox.active = true;
            weaponHitbox.setCs(frame.hitbox);
            weaponHitbox.setAngle(frame.hitboxAngle);
        }
    }
    
    private int getCurrentFrame(AnimFrame[] frames) {
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
    
    private AnimFrame[] getFrames(PlayerData playerData) {
        return getCurrentWeapon(playerData).getFramesForDirectionIndex(directionIndex);
    }
    
    private WeaponData getCurrentWeapon(PlayerData playerData) {
        return playerData.weapons.get(currentWeaponIndex);
    }
}
