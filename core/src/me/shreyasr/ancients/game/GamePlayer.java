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
    
    private Pos vel = new Pos(0, 0);
    
    public Pos pos;
    public Status currentStatus = Status.ALIVE;
    public DirectionalAnimation animation;
    public Hitbox hitbox;
    public WeaponHitbox weaponHitbox;
    public Attack currentAttack;
    public TexTransform ttc;
    public Knockback knockback;
    public PlayerStats stats;
    
    public GamePlayer(int id, PlayerData data, Pos pos, Status currentStatus, TexTransform ttc, DirectionalAnimation animation,
                      Hitbox hitbox, WeaponHitbox weaponHitbox, Attack attack, Knockback knockback, PlayerStats stats) {
        this.id = id;
        this.data = data;
        this.pos = pos;
        this.currentStatus = currentStatus;
        this.ttc = ttc;
        this.animation = animation;
        this.hitbox = hitbox;
        this.weaponHitbox = weaponHitbox;
        this.currentAttack = attack;
        this.knockback = knockback;
        this.stats = stats;
    }
    
    public GamePlayer(GamePlayer other) {
        this(other.id, other.data, new Pos(other.pos), other.currentStatus, new TexTransform(other.ttc),
                new DirectionalAnimation(other.animation), new Hitbox(other.hitbox), new WeaponHitbox(other.weaponHitbox),
                other.currentAttack.copy(), new Knockback(other.knockback), new PlayerStats(other.stats));
        this.input = other.input;
        this.lastInput = other.lastInput;
    }
    
    public void update(int deltaMillis, PlayerSet players) {
        switch (currentStatus) {
            case ALIVE:
                ttc.hide = false;
                
                if (input.moveRight) vel.x = 5;
                if (input.moveLeft) vel.x = -5;
                if (input.moveUp) vel.y = 5;
                if (input.moveDown) vel.y = -5;
            
                if (input.switchWeapons && !lastInput.switchWeapons) {
                    ((AnimatedAttack) currentAttack).nextWeaponIndex++;
                    if (((AnimatedAttack) currentAttack).nextWeaponIndex >= data.weapons.size()) {
                        ((AnimatedAttack) currentAttack).nextWeaponIndex = 0;
                    }
                }
            
                hitbox.isBeingHit = false;
                for (GamePlayer otherPlayer : players) {
                    boolean isWeaponIntersecting = otherPlayer.weaponHitbox.cs.overlaps(otherPlayer.pos, hitbox.getRect(data, pos));
                    if (otherPlayer.weaponHitbox.active && isWeaponIntersecting) {
                        if (!hitbox.isBeingHit && !knockback.isInKnockback() && currentStatus == Status.ALIVE) {
                            stats.currentHealth -= 1;
                        }
                        hitbox.isBeingHit = true;
                        knockback.hitOrigin.set(otherPlayer.pos);
                    }
                }
            
                knockback.update(data, deltaMillis, pos, hitbox.isBeingHit);
                currentAttack.update(data, deltaMillis, pos, input, weaponHitbox);
                animation.update(data, deltaMillis, vel.x != 0 || vel.y != 0, vel.getDirDegrees());
            
                if (!knockback.isInKnockback()) {
                    if (stats.currentHealth <= 0) {
                        stats.deathTimer = 0;
                        currentStatus = Status.DEAD;
                    }
                
                    pos.x += vel.x;
                    pos.y += vel.y;
                }
                break;
                
            case DEAD:
                stats.deathTimer += deltaMillis;
            
                if (stats.deathTimer > 2000) {
                    currentStatus = Status.RESPAWNING;
                    stats.respawnTimer = 4000;
                }
                break;
                
            case RESPAWNING:
                ttc.hide = true;
                stats.respawnTimer -= deltaMillis;
                if (stats.respawnTimer <= 0) {
                    pos.x = (float) (Math.random() * 500);
                    pos.y = (float) (Math.random() * 500);
                    currentStatus = Status.ALIVE;
                    stats.currentHealth = stats.maxHealth;
                }
                break;
        }
    }
    
    public void interpolateTo(GamePlayer nextPlayer, float percentageToNext) {
        pos.x = pos.x * (1 - percentageToNext) + nextPlayer.pos.x * percentageToNext;
        pos.y = pos.y * (1 - percentageToNext) + nextPlayer.pos.y * percentageToNext;
    }
    
    public enum Status {
        ALIVE, DEAD, RESPAWNING
    }
}
