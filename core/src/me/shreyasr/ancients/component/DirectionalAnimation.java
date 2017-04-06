package me.shreyasr.ancients.component;

import lombok.ToString;

@ToString
public class DirectionalAnimation extends Animation {
    
    @Override public boolean getFlipX() { return flipX; }
    @Override public boolean getFlipY() { return flipY; }
    @Override public int getSrcX() { return srcX; }
    @Override public int getSrcY() { return srcY; }
    
    private int srcX;
    private int srcY;
    private boolean flipX;
    private boolean flipY;
    private int frameTimeMillis;
    
    private DirAnim up;
    private DirAnim right;
    private DirAnim left;
    private DirAnim down;
    private DirAnim lastDirAnim;
    
    private int millisInFrame = -1;
    private int currentFrameIndex = 0;
    
    protected DirectionalAnimation() { }
    
    public DirectionalAnimation(DirectionalAnimation anim) {
        this(anim.frameTimeMillis, anim.up, anim.right, anim.left, anim.down);
        this.lastDirAnim = anim.lastDirAnim;
        this.millisInFrame = anim.millisInFrame;
        this.currentFrameIndex = anim.currentFrameIndex;
        this.srcX = anim.srcX;
        this.srcY = anim.srcY;
        this.flipX = anim.flipX;
        this.flipY = anim.flipY;
    }
    
    public DirectionalAnimation(int frameTimeMillis, DirAnim up, DirAnim right, DirAnim left, DirAnim down) {
        this.frameTimeMillis = frameTimeMillis;
        this.up = up;
        this.right = right;
        this.left = left;
        this.down = down;
        this.lastDirAnim = down;
    }
    
    @Override
    public void update(int deltaMillis, boolean moving, float facingDegrees) {
        DirAnim dirAnim = getDirAnim(moving, facingDegrees);
        lastDirAnim = dirAnim;
        
        if (moving || millisInFrame != -1) {
//            if (currentFrameIndex == dirAnim.standingFrame) {
//                currentFrameIndex = 0;
//            }
            millisInFrame += deltaMillis;
        }
        
        if (millisInFrame > frameTimeMillis) {
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
        
        int renderFrame;
        if (!moving && millisInFrame == -1) {
            renderFrame = dirAnim.standingFrame;
        } else {
            renderFrame = currentFrameIndex;
        }
    
        DirAnim.Frame currentFrame = dirAnim.frames[renderFrame];
        srcX = currentFrame.srcX;
        srcY = currentFrame.srcY;
        flipX = currentFrame.flipX;
        flipY = currentFrame.flipY;
    }
    
    private DirAnim getDirAnim(boolean moving, float facingDegrees) {
        if (!moving) {
            return lastDirAnim;
        } else {
            float normFacingDegrees = ((facingDegrees % 360) + 360) % 360;
            if (normFacingDegrees <= 45) return right;
            else if (normFacingDegrees <= 135) return up;
            else if (normFacingDegrees <= 225) return left;
            else if (normFacingDegrees <= 315) return down;
            else return right; // normFacingDegrees > 315
        }
    }
}
