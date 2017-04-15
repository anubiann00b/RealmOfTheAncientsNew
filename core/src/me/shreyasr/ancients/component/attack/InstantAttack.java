package me.shreyasr.ancients.component.attack;

import me.shreyasr.ancients.component.Attack;
import me.shreyasr.ancients.component.Pos;
import me.shreyasr.ancients.component.WeaponHitbox;
import me.shreyasr.ancients.game.PlayerData;
import me.shreyasr.ancients.network.InputData;

public class InstantAttack extends Attack {
    
    boolean attacking = false;
    float dirAttacking = 0;
    
    @Override
    public void update(PlayerData playerData, int deltaMillis, Pos pos, InputData input, WeaponHitbox weaponHitbox) {
        attacking = input.leftMouse;
        if (input.pos != null) {
            dirAttacking = input.pos.sub(pos).getDirDegrees();
        }
        
        applyFrame(playerData, weaponHitbox);
    }
    
    @Override
    public void applyFrame(PlayerData playerData, WeaponHitbox weaponHitbox) {
        weaponHitbox.active = attacking;
        weaponHitbox.setAngle(dirAttacking);
    }
    
    @Override
    public AnimFrame getCurrentAnimFrame(PlayerData playerData) {
        return null;
    }
    
    @Override
    public Attack copy() {
        return new InstantAttack();
    }
}
