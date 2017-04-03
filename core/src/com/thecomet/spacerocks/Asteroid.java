package com.thecomet.spacerocks;

public abstract class Asteroid extends Entity {
    enum AsteroidClass {
        TINY,
        SMALL,
        KINDALARGE,
        HUGE
    }

    public static Asteroid createAsteroid(AsteroidClass asteroidClass) {
        switch (asteroidClass) {
            case TINY: return new TinyAsteroid();
            case SMALL: return new HugeAsteroid();
            case KINDALARGE: return new KindaLargeAsteroid();
            case HUGE: return new HugeAsteroid();
        }
        return null;
    }

    @Override
    public void update(float timeStep) {

    }
}
