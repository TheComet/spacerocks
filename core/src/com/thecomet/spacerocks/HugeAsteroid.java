package com.thecomet.spacerocks;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;

public class HugeAsteroid extends Asteroid {

    public HugeAsteroid(SpaceRocks spaceRocks) {
        super(spaceRocks);
        createActions();
        loadLines("lines/huge_asteroid.json", 80);
        setPosition(100, 100);
    }

    private void createActions() {
        addAction(new Action() {
            @Override
            public boolean act(float delta) {
                setRotation(getRotation() + delta * getTurnSpeed());
                Vector2 movement = new Vector2(getVelocity()).scl(delta);
                setPosition(getPosition().add(movement));
                return false;
            }
        });
    }
}
