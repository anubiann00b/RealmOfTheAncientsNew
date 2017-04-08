package me.shreyasr.ancients.game;

import com.badlogic.gdx.math.Rectangle;
import lombok.ToString;

@ToString
public class PlayerData {
    
    public final int playerId;
    public final Rectangle hitboxRect;
    
    public PlayerData(int playerId, Rectangle hitboxRect) {
        this.playerId = playerId;
        this.hitboxRect = hitboxRect;
    }
}
