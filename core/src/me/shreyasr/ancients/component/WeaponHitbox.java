package me.shreyasr.ancients.component;

import lombok.ToString;
import me.shreyasr.ancients.util.CircleSlice;

@ToString
public class WeaponHitbox {
    
    public boolean active = false;
    public CircleSlice cs;
    
    protected WeaponHitbox() {
        this((CircleSlice) null);
    }
    
    public WeaponHitbox(CircleSlice cs) {
        this.cs = cs;
    }
    
    public WeaponHitbox(WeaponHitbox weaponHitbox) {
        this(new CircleSlice(weaponHitbox.cs));
        active = weaponHitbox.active;
    }
    
    public void setAngle(float angle) {
        cs.setAngle(angle);
    }
    
    public void setCs(CircleSlice cs) {
        this.cs = cs;
    }
}
