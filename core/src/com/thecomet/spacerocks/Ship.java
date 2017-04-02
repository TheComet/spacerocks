package com.thecomet.spacerocks;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Ship extends Entity {
    private OrthographicCamera camera;

    private static final float ROTATION_SPEED = 40;
    private static final float ACCELERATION = 1;

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
        shapeRenderer.translate(200, 200, 0);
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
        if (accelerate) {
            velocity.add(new Vector2(1, 0).rotate(rotation));
        } else if (decelerate) {
            velocity.sub(new Vector2(1, 0).rotate(rotation));
        }
    }

    private void updateRotation(float timeStep) {
        if (rotateLeft) {
            rotation -= timeStep * ROTATION_SPEED;
        } else if (rotateRight) {
            rotation += timeStep * ROTATION_SPEED;
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
