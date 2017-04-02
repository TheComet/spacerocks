package com.thecomet.spacerocks;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Ship extends Entity {
    private OrthographicCamera camera;

    private static final float ROTATION_SPEED = 100;
    private static final float ACCELERATION = 10;
    private static final float MAX_VELOCITY = 5;

    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    private Vector2 velocity = new Vector2();

    private boolean rotateLeft;
    private boolean rotateRight;
    private boolean accelerate;
    private boolean decelerate;

    public Ship() {
        Gdx.input.setInputProcessor(new ShipInputProcessor(this));
    }

    @Override
    public void render(Batch batch) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 1, 1, 1);
        shapeRenderer.identity();
        shapeRenderer.translate(position.x, position.y, 0);
        shapeRenderer.rotate(0, 0, 1, rotation);
        shapeRenderer.line(-10, -20, 0, 13);
        shapeRenderer.line(10f, -20, 0, 13);
        shapeRenderer.line(-8, -13.5f, 8, -13.5f);
        shapeRenderer.end();
    }

    @Override
    public void update(float timeStep) {
        updateRotation(timeStep);
        updateVelocity(timeStep);
        updatePosition();
    }

    private void updatePosition() {
        position.add(velocity);
    }

    private void updateVelocity(float timeStep) {
        Vector2 direction = new Vector2(0, 1).rotate(rotation);
        if (accelerate) {
            velocity.mulAdd(direction, timeStep * ACCELERATION);
        } else if (decelerate) {
            velocity.mulAdd(direction, - timeStep * ACCELERATION);
        }

        velocity.clamp(0, MAX_VELOCITY);
    }

    private void updateRotation(float timeStep) {
        if (rotateLeft) {
            rotation += timeStep * ROTATION_SPEED;
        } else if (rotateRight) {
            rotation -= timeStep * ROTATION_SPEED;
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

    public void setDecelerate(boolean decelerate) {
        this.decelerate = decelerate;
    }

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        shapeRenderer = null;
        camera = null;
    }
}
