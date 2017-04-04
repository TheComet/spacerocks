package com.thecomet.spacerocks;

import com.badlogic.gdx.physics.box2d.BodyDef;

public class HugeAsteroid extends Asteroid {

    public HugeAsteroid(Context context) {
        super(context);
        loadLines("lines/huge_asteroid.json", 80);
        setupPhysics(BodyDef.BodyType.DynamicBody);
        getBody().setTransform(100, 100, 0);
        getBody().setLinearVelocity(getRandomVelocity());
        getBody().setAngularVelocity(getRandomTurnSpeed() * (float)Math.PI / 180.0f);
    }
}
