package com.thecomet.spacerocks;

import com.badlogic.gdx.physics.box2d.BodyDef;

public class HugeAsteroid extends Asteroid {

    public HugeAsteroid(Context context) {
        super(context);
        loadLines("lines/huge_asteroid.json", 80);
        setupPhysics(BodyDef.BodyType.DynamicBody);
        setPosition(100, 100);
        setLinearVelocity(getRandomVelocity(5, 20));
        setAngularVelocity(getRandomTurnSpeed(-100, 100));
    }
}
