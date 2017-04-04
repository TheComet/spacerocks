package com.thecomet.spacerocks;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

public class Bullet extends Actor {
    private static final float VELOCITY = 1000;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    private Vector2 velocity;

    public Bullet(Vector2 position, Vector2 direction) {
        setPosition(position.x, position.y);
        velocity = direction.scl(VELOCITY);

        addAction(new Action() {
            @Override
            public boolean act(float delta) {
                updatePosition(delta);
                return false;
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        shapeRenderer.identity();
        shapeRenderer.translate(getX(), getY(), 0);
        shapeRenderer.rotate(0, 0, 1, getRotation());

        shapeRenderer.setColor(1, 0, 0, 1);
        shapeRenderer.circle(0, 0, 5);

        shapeRenderer.end();
        batch.begin();
    }

    private void updatePosition(float timeStep) {
        Vector2 distance = velocity.cpy().scl(timeStep);
        setPosition(getX() + distance.x, getY() + distance.y);
    }
}
