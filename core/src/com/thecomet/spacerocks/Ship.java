package com.thecomet.spacerocks;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;

public class Ship extends Entity {
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private ShipControls controls;

    private static final float ROTATION_SPEED = 100;
    private static final float ACCELERATION = 10;
    private static final float MAX_VELOCITY = 5;
    private static final float RECOIL_FORCE = 0.1f;

    private float exhaustTimer = 0;
    private boolean doDrawExhaust = false;

    private Vector2 velocity = new Vector2();

    public Ship() {
        controls = new ShipControls();
        ShipInputMapper inputMapper = new ShipInputMapper(controls);
        addListener(inputMapper);

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
        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        shapeRenderer.identity();
        shapeRenderer.translate(getX(), getY(), 0);
        shapeRenderer.rotate(0, 0, 1, getRotation());

        shapeRenderer.setColor(1, 1, 1, 1);
        shapeRenderer.line(-10, -20, 0, 13);
        shapeRenderer.line(10f, -20, 0, 13);
        shapeRenderer.line(-8, -13.5f, 8, -13.5f);

        if (doDrawExhaust) {
            shapeRenderer.line(-3, -18, 0, -26);
            shapeRenderer.line(3, -18, 0, -26);
        }

        shapeRenderer.end();
        batch.begin();
    }

    @Override
    public void update(float timeStep) {
    }
}
