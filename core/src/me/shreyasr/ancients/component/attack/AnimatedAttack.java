package me.shreyasr.ancients.component.attack;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import lombok.ToString;
import me.shreyasr.ancients.component.Attack;
import me.shreyasr.ancients.component.Pos;
import me.shreyasr.ancients.component.WeaponHitbox;
import me.shreyasr.ancients.game.PlayerData;
import me.shreyasr.ancients.network.InputData;

import java.util.stream.Stream;

@ToString
public class AnimatedAttack extends Attack implements KryoSerializable {
    
    @Override
    public void write(Kryo kryo, Output output) {
        output.writeInt(directionIndex+1, true);
        output.writeInt(attackTimer+1, true);
        output.writeInt(currentWeaponIndex, true);
    }
    
    @Override
    public void read(Kryo kryo, Input input) {
        directionIndex = input.readInt(true)-1;
        attackTimer = input.readInt(true)-1;
        currentWeaponIndex = input.readInt(true);
    }
    
    @Override
    public AnimatedAttack copy() {
        AnimatedAttack attack = new AnimatedAttack();
        attack.attackTimer = attackTimer;
        attack.directionIndex = directionIndex;
        attack.currentWeaponIndex = currentWeaponIndex;
        attack.nextWeaponIndex = nextWeaponIndex;
        return attack;
    }
    
    public transient int nextWeaponIndex = 0;
    
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
            currentWeaponIndex = nextWeaponIndex;
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
    
    @Override
    public int getNextWeaponIndex() {
        return nextWeaponIndex;
    }
    
    @Override
    public void setNextWeaponIndex(PlayerData playerData, int nextWeaponIndex) {
        if (nextWeaponIndex < 0 || nextWeaponIndex >= playerData.weapons.size()) {
            throw new AssertionError("Trying to switch to weapon at " + nextWeaponIndex);
        }
        this.nextWeaponIndex = nextWeaponIndex;
    }
    
    @Override
    public AnimFrame getCurrentAnimFrame(PlayerData playerData) {
        AnimFrame[] frames = getFrames(playerData);
        int currentFrame = getCurrentAnimFrameIndex(frames);
        return currentFrame == -1 ? null : frames[currentFrame];
    }
    
    private int getCurrentAnimFrameIndex(AnimFrame[] frames) {
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
            throw new RuntimeException("Error in AnimatedAttack, " + this);
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
