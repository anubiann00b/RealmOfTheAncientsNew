package me.shreyasr.ancients.component;

import lombok.ToString;

@ToString
public class PlayerStats {
    
    public final int maxHealth;
    public final int maxRespawnTime;
    public final int maxDeathTime;
    public int currentHealth;
    public int respawnTimer = -1;
    public int deathTimer = -1;
    
    public PlayerStats(int maxHealth, int maxRespawnTime, int maxDeathTime) {
        this.maxHealth = maxHealth;
        this.maxRespawnTime = maxRespawnTime;
        this.maxDeathTime = maxDeathTime;
        this.currentHealth = this.maxHealth;
    }
    
    public PlayerStats(PlayerStats stats) {
        this(stats.maxHealth, stats.maxRespawnTime, stats.maxDeathTime);
        this.currentHealth = stats.currentHealth;
        this.respawnTimer = stats.respawnTimer;
        this.deathTimer = stats.deathTimer;
    }
}
