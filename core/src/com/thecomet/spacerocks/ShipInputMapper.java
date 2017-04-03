package com.thecomet.spacerocks;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class ShipInputMapper extends InputListener {
    private ShipControls shipControls;

    public ShipInputMapper(ShipControls shipControls) {
        this.shipControls = shipControls;
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                shipControls.pressLeft();
                return true;
            case Input.Keys.RIGHT:
                shipControls.pressRight();
                return true;
            case Input.Keys.UP:
                shipControls.pressAccelerate();
                return true;
            case Input.Keys.SPACE:
                shipControls.pressShoot();
                return true;
            case Input.Keys.DOWN:
                shipControls.pressTeleport();
                return true;
        }

        return false;
    }

    @Override
    public boolean keyUp(InputEvent event, int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                shipControls.releaseLeft();
                return true;
            case Input.Keys.RIGHT:
                shipControls.releaseRight();
                return true;
            case Input.Keys.UP:
                shipControls.releaseAccelerate();
                return true;
        }

        return false;
    }
}
