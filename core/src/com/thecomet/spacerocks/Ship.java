package com.thecomet.spacerocks;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Ship extends Entity {
    private final SpaceRocks spaceRocks;
    private OrthographicCamera camera;

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

    public Ship(SpaceRocks spaceRocks) {
        this.spaceRocks = spaceRocks;
    }

    private void processShooting() {
        if (shooting) {
            velocity.mulAdd(getRotation(), -RECOIL_FORCE);
            spawnBullet();
        }
    }

    private void spawnBullet() {
        Bullet bullet = new Bullet(new Vector2(position), getRotation());
        bullet.create();
        spaceRocks.addEntity(bullet);
    }

    private void updatePosition() {
        position.add(velocity);
    }

    private void updateVelocity(float timeStep) {
        if (accelerate) {
            Vector2 direction = getRotation();
            velocity.mulAdd(direction, timeStep * ACCELERATION);
            updateExhaustFlicker(timeStep, false);
        } else {
            updateExhaustFlicker(timeStep, true);
        }

        velocity.clamp(0, MAX_VELOCITY);
    }

    private Vector2 getRotation() {
        return new Vector2(0, 1).rotate(rotation);
    }

    private void updateRotation(float timeStep) {
        if (rotateLeft) {
            rotation += timeStep * ROTATION_SPEED;
        } else if (rotateRight) {
            rotation -= timeStep * ROTATION_SPEED;
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
    public void create() {
        camera = new OrthographicCamera();
        Gdx.input.setInputProcessor(new ShipInputProcessor(this));
    }

    @Override
    public void dispose() {
        camera = null;
    }

    @Override
    public void render(Batch batch, ShapeRenderer shapeRenderer) {
        shapeRenderer.identity();
        shapeRenderer.translate(position.x, position.y, 0);
        shapeRenderer.rotate(0, 0, 1, rotation);

        shapeRenderer.setColor(1, 1, 1, 1);
        shapeRenderer.line(-10, -20, 0, 13);
        shapeRenderer.line(10f, -20, 0, 13);
        shapeRenderer.line(-8, -13.5f, 8, -13.5f);

        if (doDrawExhaust) {
            shapeRenderer.line(-3, -18, 0, -26);
            shapeRenderer.line(3, -18, 0, -26);
        }
    }

    @Override
    public void update(float timeStep) {
        processShooting();
        updateRotation(timeStep);
        updateVelocity(timeStep);
        updatePosition();
    }
}
