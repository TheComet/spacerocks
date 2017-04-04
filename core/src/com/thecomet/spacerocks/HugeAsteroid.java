package com.thecomet.spacerocks;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class HugeAsteroid extends Asteroid {

    private final TextureRegion textureRegion;

    public HugeAsteroid() {
        loadLines("lines/huge_asteroid.json", 80);
        textureRegion = textureRegions.get("asteroid");
        setPosition(100, 100);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        drawTextureRegion(batch, textureRegion);
    }
}
