package me.shreyasr.ancients.component;

import me.shreyasr.ancients.game.PlayerData;

public abstract class Animation {
    
    public abstract boolean getFlipX();
    public abstract boolean getFlipY();
    public abstract int getSrcX();
    public abstract int getSrcY();
    
    public abstract void update(PlayerData playerData, int deltaMillis, boolean moving, float facingDir);
    public void overrideAnimation(int newStandingFrame) { }
    public void resetStandingFrame() { }
}
