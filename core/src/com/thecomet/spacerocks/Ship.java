package com.thecomet.spacerocks;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Ship extends Entity {
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    private static final float ROTATION_SPEED = 100;
    private static final float ACCELERATION = 10;
    private static final float MAX_VELOCITY = 5;
    private static final float RECOIL_FORCE = 0.1f;

    private boolean rotateLeft;
    private boolean rotateRight;
    private boolean accelerate;

    private float exhaustTimer = 0;
    private boolean doDrawExhaust = false;
    private boolean shooting;

    private Vector2 velocity = new Vector2();

    public Ship() {
        addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                switch (keycode) {
                    case Input.Keys.LEFT:
                        setRotateLeft(true);
                        return true;
                    case Input.Keys.RIGHT:
                        setRotateRight(true);
                        return true;
                    case Input.Keys.UP:
                        setAccelerate(true);
                        return true;
                    case Input.Keys.SPACE:
                        setShooting(true);
                        return true;
                }

                return false;
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                switch (keycode) {
                    case Input.Keys.LEFT:
                        setRotateLeft(false);
                        return true;
                    case Input.Keys.RIGHT:
                        setRotateRight(false);
                        return true;
                    case Input.Keys.UP:
                        setAccelerate(false);
                        return true;
                    case Input.Keys.SPACE:
                        setShooting(false);
                        return true;
                }

                return false;
            }
        });
    }

    private void processShooting() {
        Vector2 direction = calculateDirection();
        if (shooting) {
            velocity.mulAdd(direction, -RECOIL_FORCE);
            Bullet bullet = new Bullet(new Vector2(getX(), getY()), direction);
            getStage().addActor(bullet);
        }
    }

    private void updatePosition() {
        setPosition(getX() + velocity.x, getY() + velocity.y);
    }

    private void updateVelocity(float timeStep) {
        if (accelerate) {
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
        if (rotateLeft) {
            setRotation(getRotation() + timeStep * ROTATION_SPEED);
        } else if (rotateRight) {
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

    public void setRotateLeft(boolean rotateLeft) {
        this.rotateLeft = rotateLeft;
    }

    public void setRotateRight(boolean rotateRight) {
        this.rotateRight = rotateRight;
    }

    public void setAccelerate(boolean accelerate) {
        this.accelerate = accelerate;
    }

    public void setShooting(boolean shooting) {
        this.shooting = shooting;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

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
        processShooting();
        updateRotation(timeStep);
        updateVelocity(timeStep);
        updatePosition();
    }
}
