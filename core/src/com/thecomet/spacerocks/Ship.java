package com.thecomet.spacerocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.HashMap;

public class Ship extends Actor {
    private TextureRegion shipTextureRegion;
    private TextureRegion exhaustTextureRegion;
    private ShipControls controls;

    private static final float ROTATION_SPEED = 100;
    private static final float ACCELERATION = 10;
    private static final float MAX_VELOCITY = 5;
    private static final float RECOIL_FORCE = 0.1f;

    private float exhaustTimer = 0;
    private boolean doDrawExhaust = false;

    private Vector2 velocity = new Vector2();

    public Ship() {
        setupControls();
        loadTextures();
        createActions();
    }

    private void setupControls() {
        // Input mapper implements the listener interface, so add it as a listener to this actor so it receives input
        controls = new ShipControls();
        ShipInputMapper inputMapper = new ShipInputMapper(controls);
        addListener(inputMapper);
    }

    private void loadTextures() {
        int shipScale = 32;

        Lines lines = Lines.load("lines/ship.json");
        HashMap<String, TextureRegion> regions = lines.renderToTextures(shipScale);

        shipTextureRegion = regions.get("ship");
        exhaustTextureRegion = regions.get("exhaust");

        Vector2 origin = lines.calculateOrigin(shipScale);
        setOrigin(origin.x, origin.y);
    }

    private void createActions() {
        addAction(new Action() {
            @Override
            public boolean act(float delta) {
                // Advance edge detection logic in input mapper
                controls.nextFrame();

                // Update ship movement and other behaviour
                processShooting();
                updateRotation(delta);
                updateVelocity(delta);
                updatePosition();
                return false;
            }
        });
    }

    private void processShooting() {
        Vector2 direction = calculateDirection();
        if (controls.getShoot()) {
            velocity.mulAdd(direction, -RECOIL_FORCE);
            Bullet bullet = new Bullet(new Vector2(getX(), getY()), direction);
            getStage().addActor(bullet);
        }
    }

    private void updatePosition() {
        setPosition(getX() + velocity.x, getY() + velocity.y);
    }

    private void updateVelocity(float timeStep) {
        if (controls.getAccelerate()) {
            Vector2 direction = calculateDirection();
            velocity.mulAdd(direction, timeStep * ACCELERATION);
            updateExhaustFlicker(timeStep, false);
        } else {
            updateExhaustFlicker(timeStep, true);
        }

        velocity.clamp(0, MAX_VELOCITY);
    }

    private Vector2 calculateDirection() {
        return new Vector2(0, 1).rotate(getRotation());
    }

    private void updateRotation(float timeStep) {
        if (controls.getTurnLeft()) {
            setRotation(getRotation() + timeStep * ROTATION_SPEED);
        } else if (controls.getTurnRight()) {
            setRotation(getRotation() - timeStep * ROTATION_SPEED);
        }
    }

    private void updateExhaustFlicker(float timeStep, boolean reset) {
        if (reset) {
            doDrawExhaust = false;
            return;
        }

        exhaustTimer += timeStep;
        doDrawExhaust = exhaustTimer >= 0.07;
        if (exhaustTimer >= 0.14) {
            exhaustTimer = 0;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(shipTextureRegion, getX(), getY(), getOriginX(), getOriginY(),
                shipTextureRegion.getRegionWidth(), shipTextureRegion.getRegionHeight(),
                1, 1, getRotation());
        if (doDrawExhaust) {
            batch.draw(exhaustTextureRegion, getX(), getY(), getOriginX(), getOriginY(),
                    exhaustTextureRegion.getRegionWidth(), exhaustTextureRegion.getRegionHeight(),
                    1, 1, getRotation());
        }
    }
}
