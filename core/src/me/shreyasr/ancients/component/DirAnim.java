package me.shreyasr.ancients.component;

import lombok.ToString;

@ToString(includeFieldNames = false)
public class DirAnim {
    
    public int standingFrame;
    public Frame[] frames;
    
    protected DirAnim() { }

    public DirAnim(int standingFrame, Frame... frames) {
        this.standingFrame = standingFrame;
        this.frames = frames;
    }
    
    @ToString
    public static class Frame {
        
        public int srcX;
        public int srcY;
        public boolean flipX;
        public boolean flipY;
        
        protected Frame() { }

        public Frame(int srcX, int srcY) {
            this(srcX, srcY, false, false);
        }

        public Frame(int srcX, int srcY, boolean flipX, boolean flipY) {
            this.srcX = srcX;
            this.srcY = srcY;
            this.flipX = flipX;
            this.flipY = flipY;
        }
    }
}
