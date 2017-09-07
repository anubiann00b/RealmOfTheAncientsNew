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
    public boolean isAttacking() {
        return attacking;
    }
    
    @Override
    public void update(PlayerData playerData, int deltaMillis, Pos pos, InputData input, WeaponHitbox weaponHitbox) {
        attacking = input.attack;
        if (input.mousePos != null) {
            dirAttacking = input.mousePos.sub(pos).getDirDegrees();
        }
        
        applyFrame(playerData, weaponHitbox);
    }
    
    @Override
    public void applyFrame(PlayerData playerData, WeaponHitbox weaponHitbox) {
        weaponHitbox.active = attacking;
        weaponHitbox.setAngle(dirAttacking);
    }
    
    @Override
    public int getNextWeaponIndex() {
        return 0;
    }
    
    @Override
    public void setNextWeaponIndex(PlayerData playerData, int nextWeaponIndex) {
        
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
