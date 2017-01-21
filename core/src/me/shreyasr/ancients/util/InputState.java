package me.shreyasr.ancients.util;

public enum InputState {
    STATUS_UP, STATUS_DOWN, STATUS_PRESSED, STATUS_RELEASED;

    public boolean isKeyDown() {
        return this == STATUS_DOWN || this == STATUS_PRESSED;
    }

    public boolean isKeyUp() {
        return this == STATUS_UP || this == STATUS_RELEASED;
    }

    public boolean isKeyJustPressed() {
        return this == STATUS_PRESSED;
    }

    public boolean isKeyJustReleased() {
        return this == STATUS_RELEASED;
    }
}
