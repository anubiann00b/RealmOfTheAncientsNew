package me.shreyasr.ancients.game;

import lombok.ToString;
import me.shreyasr.ancients.component.*;
import me.shreyasr.ancients.component.attack.AnimatedAttack;
import me.shreyasr.ancients.network.InputData;

@ToString(includeFieldNames = false, exclude = { "data" })
public class GamePlayer {
    
    public int id;
    
    public transient PlayerData data;
    public transient InputData input;
    public transient InputData lastInput;
    
    public Pos pos;
    public Pos vel = new Pos(0, 0);
    public DirectionalAnimation animation;
    public Hitbox hitbox;
    public WeaponHitbox weaponHitbox;
    public Attack currentAttack;
    public TexTransform ttc;
    public Knockback knockback;
    public PlayerStats stats;
    
    public GamePlayer(int id, PlayerData data, Pos pos, TexTransform ttc, DirectionalAnimation animation,
                      Hitbox hitbox, WeaponHitbox weaponHitbox, Attack attack, Knockback knockback, PlayerStats stats) {
        this.id = id;
        this.data = data;
        this.pos = pos;
        this.ttc = ttc;
        this.animation = animation;
        this.hitbox = hitbox;
        this.weaponHitbox = weaponHitbox;
        this.currentAttack = attack;
        this.knockback = knockback;
        this.stats = stats;
    }
    
    public GamePlayer(GamePlayer other) {
        this(other.id, other.data, new Pos(other.pos), new TexTransform(other.ttc),
                new DirectionalAnimation(other.animation), new Hitbox(other.hitbox), new WeaponHitbox(other.weaponHitbox),
                other.currentAttack.copy(), new Knockback(other.knockback), new PlayerStats(other.stats));
        this.input = other.input;
        this.lastInput = other.lastInput;
    }
    
    public void update(int deltaMillis, PlayerSet players) {
        if (input.moveRight) vel.x = 5;
        if (input.moveLeft) vel.x = -5;
        if (input.moveUp) vel.y = 5;
        if (input.moveDown) vel.y = -5;
        
        if (input.switchWeapons && !lastInput.switchWeapons) {
            ((AnimatedAttack)currentAttack).nextWeaponIndex++;
            if (((AnimatedAttack)currentAttack).nextWeaponIndex >= data.weapons.size()) {
                ((AnimatedAttack)currentAttack).nextWeaponIndex = 0;
            }
        }
        
        hitbox.isBeingHit = false;
        for (GamePlayer otherPlayer : players) {
             if (otherPlayer.weaponHitbox.active && otherPlayer.weaponHitbox.cs.overlaps(otherPlayer.pos, hitbox.getRect(data, pos))) {
                 if (!hitbox.isBeingHit && !knockback.isInKnockback()) stats.currentHealth -= 1;
                 hitbox.isBeingHit = true;
                 knockback.hitOrigin.set(otherPlayer.pos);
             }
        }
        
        knockback.update(data, deltaMillis, pos, hitbox.isBeingHit);
        currentAttack.update(data, deltaMillis, pos, input, weaponHitbox);
        animation.update(data, deltaMillis, vel.x != 0 || vel.y != 0, vel.getDirDegrees());
        
        pos.x += vel.x;
        pos.y += vel.y;
    }
    
    public void interpolateTo(GamePlayer nextPlayer, float percentageToNext) {
        pos.x = pos.x * (1 - percentageToNext) + nextPlayer.pos.x * percentageToNext;
        pos.y = pos.y * (1 - percentageToNext) + nextPlayer.pos.y * percentageToNext;
    }
}
