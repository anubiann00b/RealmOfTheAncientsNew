package me.shreyasr.ancients.component;

import lombok.ToString;
import me.shreyasr.ancients.game.PlayerData;
import me.shreyasr.ancients.util.Utils;

@ToString
public class Knockback {
    
    public Pos hitOrigin;
    public Pos startPos;
    public int timeElapsed = -1;
    
    public Knockback(Pos hitOrigin) {
        this.hitOrigin = hitOrigin;
    }
    
    public Knockback(Knockback other) {
        this(other.hitOrigin);
        this.timeElapsed = other.timeElapsed;
        this.startPos = other.startPos;
    }
    
    public void update(PlayerData playerData, int deltaMillis, Pos pos, boolean isHit) {
        if (isHit && timeElapsed == -1) {
            timeElapsed = 0;
            startPos = pos;
        }
        
        if (timeElapsed >= 0) {
            timeElapsed += deltaMillis;
            
            Pos normalizedDir = startPos.sub(hitOrigin).normalize();
            float distanceTravelled = playerData.knockbackDistance * interpolatedPercentDone(playerData);
    
            pos.x = startPos.x + distanceTravelled * normalizedDir.x;
            pos.y = startPos.y + distanceTravelled * normalizedDir.y;
        }
        
        if (timeElapsed > playerData.knockbackDuration) {
            timeElapsed = -1;
        }
    }
    
    private float interpolatedPercentDone(PlayerData playerData) {
        float percent = percentDone(playerData);
        return Utils.clamp(0, 2*percent - percent*percent, 1);
    }
    
    /** True if the player is currently under a knockback effect, false otherwise */
    public boolean isInKnockback() {
        return timeElapsed != -1;
    }
    
    /** A value from 0 to 1, representing the progress of the knockback effect */
    public float percentDone(PlayerData playerData) {
        return Utils.clamp(0, (float) timeElapsed / playerData.knockbackDuration, 1);
    }
}
