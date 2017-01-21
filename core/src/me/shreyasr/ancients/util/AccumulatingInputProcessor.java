package me.shreyasr.ancients.util;

import com.badlogic.gdx.InputProcessor;

public class AccumulatingInputProcessor implements InputProcessor {

    private InputState[] keys = new InputState[256];

    public AccumulatingInputProcessor(int... keysListened) {
        for (int key : keysListened) {
            keys[key] = InputState.STATUS_UP;
        }
    }

    public void update() {
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] == InputState.STATUS_PRESSED) {
                keys[i] = InputState.STATUS_DOWN;
            }
            if (keys[i] == InputState.STATUS_RELEASED) {
                keys[i] = InputState.STATUS_UP;
            }
        }
    }

    public InputState get(int key) {
        return keys[key];
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keys[keycode] != null) {
            keys[keycode] = InputState.STATUS_PRESSED;
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keys[keycode] != null) {
            keys[keycode] = InputState.STATUS_RELEASED;
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
