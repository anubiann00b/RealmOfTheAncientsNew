package me.shreyasr.ancients.component;

public class PlayerStats {
    
    public final int maxHealth;
    public int currentHealth;
    
    public PlayerStats(int maxHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = this.maxHealth;
    }
    
    public PlayerStats(PlayerStats stats) {
        this(stats.maxHealth);
        this.currentHealth = stats.currentHealth;
    }
}
