package com.thecomet.spacerocks;

import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Asteroid extends Actor {
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
}
