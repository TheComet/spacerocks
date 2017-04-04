package com.thecomet.spacerocks;

import com.badlogic.gdx.math.Vector2;

public abstract class Asteroid extends LineEntity {
    private static final float MAX_ROTATION_SPEED = 100;
    private static final float MIN_ROTATION_SPEED = -100;
    private static final float MIN_SPEED = 10;
    private static final float MAX_SPEED = 80;

    private float turnSpeed;
    private Vector2 velocity = new Vector2();

    enum AsteroidClass {
        TINY,
        SMALL,
        KINDALARGE,
        HUGE
    }

    public static Asteroid createAsteroid(SpaceRocks spaceRocks, AsteroidClass asteroidClass) {
        switch (asteroidClass) {
            case TINY: return new TinyAsteroid(spaceRocks);
            case SMALL: return new HugeAsteroid(spaceRocks);
            case KINDALARGE: return new KindaLargeAsteroid(spaceRocks);
            case HUGE: return new HugeAsteroid(spaceRocks);
        }
        return null;
    }

    public Asteroid(SpaceRocks spaceRocks) {
        super(spaceRocks);
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
