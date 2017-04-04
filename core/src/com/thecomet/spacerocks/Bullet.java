package com.thecomet.spacerocks;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;

public class Bullet extends LineEntity {
    private static final float VELOCITY = 900;

    private Vector2 velocity;

    public Bullet(SpaceRocks spaceRocks) {
        super(spaceRocks);

        loadLines("lines/bullet.json", 12);

        addAction(new Action() {
            @Override
            public boolean act(float delta) {
                updatePosition(delta);
                return false;
            }
        });
    }

    public void setDirection(Vector2 direction) {
        velocity = direction.scl(VELOCITY);
    }

    private void updatePosition(float timeStep) {
        Vector2 distance = velocity.cpy().scl(timeStep);
        setPosition(getX() + distance.x, getY() + distance.y);
    }
}
