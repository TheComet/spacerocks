package com.thecomet.spacerocks;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

/**
 * Created by ferdi on 02.04.17.
 */
public class ShipInputProcessor implements InputProcessor {
    private final Ship ship;

    public ShipInputProcessor(Ship ship) {
        this.ship = ship;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                ship.setRotateLeft(true);
                return true;
            case Input.Keys.RIGHT:
                ship.setRotateRight(true);
                return true;
            case Input.Keys.UP:
                ship.setAccelerate(true);
                return true;
            case Input.Keys.DOWN:
                ship.setDecelerate(true);
                return true;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                ship.setRotateLeft(false);
                return true;
            case Input.Keys.RIGHT:
                ship.setRotateRight(false);
                return true;
            case Input.Keys.UP:
                ship.setAccelerate(false);
                return true;
            case Input.Keys.DOWN:
                ship.setDecelerate(false);
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
