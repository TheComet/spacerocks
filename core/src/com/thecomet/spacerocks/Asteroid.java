package com.thecomet.spacerocks;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

abstract class Asteroid extends PhysicsEntity {
    enum Type {
        TINY,
        SMALL,
        KINDALARGE,
        HUGE
    }

    public static Asteroid createAsteroid(Context context, Type type) {
        Asteroid asteroid;
        switch (type) {
            case TINY:       asteroid = new TinyAsteroid(context); break;
            case SMALL:      asteroid = new HugeAsteroid(context); break;
            case KINDALARGE: asteroid = new KindaLargeAsteroid(context); break;
            case HUGE:       asteroid = new HugeAsteroid(context); break;
            default: throw new RuntimeException("Unhandled asteroid type creation");
        }

        context.stage.addActor(asteroid);
        return asteroid;
    }

    public Asteroid(Context context) {
        super(context);
    }

    @Override
    protected void configureBody(BodyDef bodyDef) {
        bodyDef.type = BodyDef.BodyType.DynamicBody;
    }

    @Override
    protected void configureFixture(FixtureDef fixtureDef) {
        fixtureDef.density = 0.3f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;
        fixtureDef.filter.categoryBits = PhysicsEntity.MASK_ENTITY;
    }
}
