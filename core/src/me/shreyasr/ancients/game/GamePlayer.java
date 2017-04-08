package me.shreyasr.ancients.game;

import lombok.ToString;
import me.shreyasr.ancients.Asset;
import me.shreyasr.ancients.component.*;
import me.shreyasr.ancients.network.InputData;

@ToString(includeFieldNames = false, exclude = { "asset" })
public class GamePlayer {
    
    public int id;
    
    public transient PlayerData data;
    
    public void setPlayerData(PlayerData data) {
        this.data = data;
    }
    
    public InputData input;
    
    public Asset asset;
    public Pos pos;
    public Pos vel = new Pos(0, 0);
    public DirectionalAnimation animation;
    public Hitbox hitbox;
    public WeaponHitbox weaponHitbox;
    public Attack currentAttack;
    public TexTransform ttc;
    
    public GamePlayer() { }
    
    public GamePlayer(int id, PlayerData data, Asset asset, Pos pos, TexTransform ttc, DirectionalAnimation animation,
                      Hitbox hitbox, WeaponHitbox weaponHitbox, Attack attack) {
        this.id = id;
        this.asset = asset;
        this.pos = pos;
        this.ttc = ttc;
        this.animation = animation;
        this.hitbox = hitbox;
        this.weaponHitbox = weaponHitbox;
        this.currentAttack = attack;
        this.setPlayerData(data);
    }
    
    public GamePlayer(GamePlayer other) {
        this(other.id, other.data, other.asset, new Pos(other.pos), new TexTransform(other.ttc),
                new DirectionalAnimation(other.animation), new Hitbox(other.hitbox), new WeaponHitbox(other.weaponHitbox),
                other.currentAttack.copy());
        this.input = other.input;
    }
    
    public void update(int deltaMillis, PlayerSet players) {
        if (input.d) vel.x = 5;
        if (input.a) vel.x = -5;
        if (input.w) vel.y = 5;
        if (input.s) vel.y = -5;
        
        currentAttack.update(deltaMillis, pos, input, weaponHitbox);
        
        animation.update(deltaMillis, vel.x != 0 || vel.y != 0, vel.getDirDegrees());
    
        hitbox.isBeingHit = false;
        for (GamePlayer player : players) {
             if (player.weaponHitbox.active && player.weaponHitbox.cs.overlaps(player.pos, hitbox.getRect(data, pos))) {
                 hitbox.isBeingHit = true;
             }
        }
        
        pos.x += vel.x;
        pos.y += vel.y;
    }
    
    public void interpolateTo(GamePlayer nextPlayer, float percentageToNext) {
        pos.x = pos.x * (1 - percentageToNext) + nextPlayer.pos.x * percentageToNext;
        pos.y = pos.y * (1 - percentageToNext) + nextPlayer.pos.y * percentageToNext;
    }
}
