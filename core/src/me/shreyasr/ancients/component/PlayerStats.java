package me.shreyasr.ancients.component;

import lombok.ToString;

@ToString
public class PlayerStats {
    
    public final int maxHealth;
    public int currentHealth;
    public int deathTimer = -1;
    public int respawnTimer = -1;
    
    public PlayerStats(int maxHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = this.maxHealth;
    }
    
    public PlayerStats(PlayerStats stats) {
        this(stats.maxHealth);
        this.currentHealth = stats.currentHealth;
        this.deathTimer = stats.deathTimer;
        this.respawnTimer = stats.respawnTimer;
    }
}
