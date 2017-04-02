package com.thecomet.spacerocks;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by ferdi on 02.04.17.
 */
public class Bullet extends Entity {
    private static final float VELOCITY = 1000;

    private Vector2 velocity;

    public Bullet(Vector2 position, Vector2 direction) {
        this.position = position;
        velocity = direction.scl(VELOCITY);
    }

    @Override
    public void create() {}

    @Override
    public void dispose() {}

    @Override
    public void render(Batch batch, ShapeRenderer shapeRenderer) {
        shapeRenderer.identity();
        shapeRenderer.translate(position.x, position.y, 0);
        shapeRenderer.rotate(0, 0, 1, rotation);

        shapeRenderer.setColor(1, 0, 0, 1);
        shapeRenderer.circle(0, 0, 5);
    }

    @Override
    public void update(float timeStep) {
        updatePosition(timeStep);
    }

    private void updatePosition(float timeStep) {
        position.add(velocity.cpy().scl(timeStep));
    }
}
