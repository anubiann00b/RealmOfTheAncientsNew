package me.shreyasr.ancients.component;

import com.esotericsoftware.minlog.Log;
import lombok.ToString;
import me.shreyasr.ancients.game.PlayerData;

@ToString
public class DirectionalAnimation extends Animation {
    
    public enum Direction {
        RIGHT, UP, LEFT, DOWN
    }
    
    @Override public boolean getFlipX(PlayerData playerData) { return getCurrentFrame(playerData).flipX; }
    @Override public boolean getFlipY(PlayerData playerData) { return getCurrentFrame(playerData).flipY; }
    @Override public int getSrcX(PlayerData playerData) { return getCurrentFrame(playerData).srcX; }
    @Override public int getSrcY(PlayerData playerData) { return getCurrentFrame(playerData).srcY; }
    
    private DirAnim.Frame getCurrentFrame(PlayerData playerData) {
        DirAnim dirAnim = getDirAnim(playerData, lastDir);
        return dirAnim.frames[renderFrame];
    }
    
    private Direction lastDir;
    
    private int millisInFrame = -1;
    private int renderFrame;
    
    private transient int currentFrameIndex = 0;
    
    public DirectionalAnimation(DirectionalAnimation anim) {
        this();
        this.lastDir = anim.lastDir;
        this.millisInFrame = anim.millisInFrame;
        this.renderFrame = anim.renderFrame;
        this.currentFrameIndex = anim.currentFrameIndex;
    }
    
    public DirectionalAnimation() {
        this.lastDir = Direction.DOWN;
    }
    
    @Override
    public void update(PlayerData playerData, int deltaMillis, boolean moving, float facingDegrees) {
        Direction dir = getDir(moving, facingDegrees);
        DirAnim dirAnim = getDirAnim(playerData, dir);
        lastDir = dir;
        
        if (moving || millisInFrame != -1) {
            millisInFrame += deltaMillis;
        }
        
        if (millisInFrame > playerData.animFrameTimeMillis) {
            millisInFrame = -1;
            if (moving) {
                currentFrameIndex += 1;
            } else {
                currentFrameIndex = 0;
            }
        }
        
        if (currentFrameIndex >= dirAnim.frames.length) {
            currentFrameIndex = 0;
        }
        
        if (!moving && millisInFrame == -1) {
            renderFrame = dirAnim.standingFrame;
        } else {
            renderFrame = currentFrameIndex;
        }
    }
    
    @Override
    public void setStandingAnimFrame(PlayerData playerData, float facingDegrees) {
        Direction dir = getDir(true, facingDegrees);
        DirAnim dirAnim = getDirAnim(playerData, dir);
        lastDir = dir;
        renderFrame = dirAnim.standingFrame;
    }
    
    private Direction getDir(boolean moving, float facingDegrees) {
        if (!moving) {
            return lastDir;
        } else {
            float normFacingDegrees = ((facingDegrees % 360) + 360) % 360;
            if (normFacingDegrees <= 45) return Direction.RIGHT;
            else if (normFacingDegrees <= 135) return Direction.UP;
            else if (normFacingDegrees <= 225) return Direction.LEFT;
            else if (normFacingDegrees <= 315) return Direction.DOWN;
            else return Direction.RIGHT; // normFacingDegrees > 315
        }
    }
    
    private DirAnim getDirAnim(PlayerData playerData, Direction dir) {
        switch (dir) {
            case RIGHT: return playerData.right;
            case UP: return playerData.up;
            case LEFT: return playerData.left;
            case DOWN: return playerData.down;
        }
        Log.error("directionalanimation", "Switch on Direction enum fell through: " + dir + " " + playerData);
        throw new RuntimeException("Switch on Direction enum fell through: " + dir + " " + playerData);
    }
}
