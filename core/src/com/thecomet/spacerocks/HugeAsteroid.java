package com.thecomet.spacerocks;

public class HugeAsteroid extends Asteroid {

    public HugeAsteroid(Context context) {
        super(context);
        loadLines("lines/huge_asteroid.json", 80);
        setupPhysics();
    }
}
