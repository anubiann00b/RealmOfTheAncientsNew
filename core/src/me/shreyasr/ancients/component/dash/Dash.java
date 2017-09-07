package me.shreyasr.ancients.component.dash;

import me.shreyasr.ancients.component.Pos;
import me.shreyasr.ancients.game.PlayerData;
import me.shreyasr.ancients.network.InputData;

public abstract class Dash {
    
    public abstract Dash copy();
    public abstract void beginDash(PlayerData playerData, int deltaMillis, Pos pos, InputData input);
    public abstract void cancel();
    public abstract void update(PlayerData data, int deltaMillis, Pos pos);
}
