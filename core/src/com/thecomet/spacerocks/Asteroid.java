package com.thecomet.spacerocks;

import com.badlogic.gdx.math.Vector2;

public abstract class Asteroid extends LineEntity {
    private static final float MAX_ROTATION_SPEED = 100;
    private static final float MIN_ROTATION_SPEED = -100;
    private static final float MIN_SPEED = 10;
    private static final float MAX_SPEED = 80;

    private float turnSpeed;
    private Vector2 velocity = new Vector2();

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
        turnSpeed = getRandomTurnSpeed();
        velocity = getRandomVelocity();
    }

    private Vector2 getRandomVelocity() {
        return new Vector2().setToRandomDirection().scl((float) (Math.random() * (MAX_SPEED - MIN_SPEED)) + MIN_SPEED);
    }

    private float getRandomTurnSpeed() {
        return (float) (Math.random() * (MAX_ROTATION_SPEED - MIN_ROTATION_SPEED)) + MIN_ROTATION_SPEED;
    }

    public float getTurnSpeed() {
        return turnSpeed;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }
}
