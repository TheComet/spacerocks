package com.thecomet.spacerocks;

import com.badlogic.gdx.math.Vector2;

public class Util {
    public static Vector2 getRandomVelocity(float min, float max) {
        return new Vector2(1, 0)
                .setToRandomDirection()
                .scl((float)(Math.random() * (max - min)) + min);
    }
}
