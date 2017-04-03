package me.shreyasr.ancients.component;

public class DirAnim {
    
    public Frame[] frames;
    public int standingFrame;
    
    protected DirAnim() { }

    public DirAnim(int standingFrame, Frame... frames) {
        this.standingFrame = standingFrame;
        this.frames = frames;
    }

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
