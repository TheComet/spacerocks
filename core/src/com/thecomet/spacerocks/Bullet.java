package com.thecomet.spacerocks;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;

public class Bullet extends LineEntity {
    private static final float VELOCITY = 900;

    private Vector2 velocity;

    public Bullet(Vector2 position, Vector2 direction) {
        setPosition(position.x, position.y);
        velocity = direction.scl(VELOCITY);

        loadLines("lines/bullet.json", 12);

        addAction(new Action() {
            @Override
            public boolean act(float delta) {
                updatePosition(delta);
                return false;
            }
        });
    }

    private void updatePosition(float timeStep) {
        Vector2 distance = velocity.cpy().scl(timeStep);
        setPosition(getX() + distance.x, getY() + distance.y);
    }
}
