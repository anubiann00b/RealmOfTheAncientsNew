package me.shreyasr.ancients.component;

import com.badlogic.gdx.math.Rectangle;
import lombok.ToString;
import me.shreyasr.ancients.game.PlayerData;

@ToString
public class Hitbox {
    
    public boolean isBeingHit = false;
    
    public Hitbox() { }
    
    public Hitbox(Hitbox hitbox) {
        this();
        isBeingHit = hitbox.isBeingHit;
    }
    
    public Rectangle getRect(PlayerData data, Pos pos) {
        Rectangle rect = data.hitboxRect;
        return new Rectangle(rect).setPosition(pos.x + rect.x, pos.y + rect.y);
    }
}
