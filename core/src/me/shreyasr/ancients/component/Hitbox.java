package me.shreyasr.ancients.component;

import com.badlogic.gdx.math.Rectangle;
import lombok.ToString;

@ToString
public class Hitbox {
    
    public boolean isBeingHit = false;
    protected final Rectangle rect;
    
    protected Hitbox() {
        this((Rectangle) null);
    }
    
    public Hitbox(Rectangle rect) {
        this.rect = rect;
    }
    
    public Hitbox(Hitbox hitbox) {
        this(new Rectangle(hitbox.rect));
        isBeingHit = hitbox.isBeingHit;
    }
    
    public Rectangle getRect(Pos pos) {
        return new Rectangle(rect).setPosition(pos.x + rect.x, pos.y + rect.y);
    }
}
