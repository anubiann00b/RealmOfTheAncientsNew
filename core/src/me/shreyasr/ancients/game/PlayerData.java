package me.shreyasr.ancients.game;

import com.badlogic.gdx.math.Rectangle;
import lombok.ToString;
import me.shreyasr.ancients.Asset;
import me.shreyasr.ancients.component.DirAnim;
import me.shreyasr.ancients.component.attack.AttackDirections;

@ToString
public class PlayerData {
    
    public final int playerId;
    public final Asset asset;
    public final Rectangle hitboxRect;
    
    public final int animFrameTimeMillis;
    public final DirAnim up;
    public final DirAnim right;
    public final DirAnim left;
    public final DirAnim down;
    
    public final AttackDirections attackDirections;
    
    public final int knockbackDuration;
    public final int knockbackDistance;
    
    public PlayerData(int playerId, Asset asset, Rectangle hitboxRect, int knockbackDuration, int knockbackDistance,
                      int animFrameTimeMillis, DirAnim up, DirAnim right, DirAnim left, DirAnim down,
                      AttackDirections attackDirections) {
        this.playerId = playerId;
        this.asset = asset;
        this.knockbackDuration = knockbackDuration;
        this.knockbackDistance = knockbackDistance;
        this.hitboxRect = hitboxRect;
        this.animFrameTimeMillis = animFrameTimeMillis;
        this.up = up;
        this.right = right;
        this.left = left;
        this.down = down;
        this.attackDirections = attackDirections;
    }
}
