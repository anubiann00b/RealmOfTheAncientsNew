package me.shreyasr.ancients.util;

import com.badlogic.gdx.InputProcessor;

public class AccumulatingInputProcessor implements InputProcessor {

    private int[] keys = new int[256];

    public AccumulatingInputProcessor(int... keysListened) {
        for (int i = 0; i < keys.length; i++) {
            keys[i] = -1;
        }
        for (int keyListened : keysListened) {
            keys[keyListened] = 0;
        }
    }

    public boolean get(int key) {
        return keys[key] == 1;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keys[keycode] != -1) {
            keys[keycode] = 1;
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keys[keycode] != -1) {
            keys[keycode] = 0;
            return true;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
