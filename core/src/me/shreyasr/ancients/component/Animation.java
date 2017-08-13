package me.shreyasr.ancients.component;

import me.shreyasr.ancients.game.PlayerData;

public abstract class Animation {
    
    public abstract boolean getFlipX(PlayerData playerData);
    public abstract boolean getFlipY(PlayerData playerData);
    public abstract int getSrcX(PlayerData playerData);
    public abstract int getSrcY(PlayerData playerData);
    
    public abstract void update(PlayerData playerData, int deltaMillis, boolean moving, float facingDir);
    public void overrideAnimation(int newStandingFrame) { }
    public void resetStandingFrame() { }
    
    public abstract void setStandingAnimFrame(PlayerData playerData, float facingDegrees);
}
