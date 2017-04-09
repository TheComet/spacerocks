package com.thecomet.spacerocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class BackgroundSprinkles extends Entity {
    private Texture sprinkleTexture;
    public BackgroundSprinkles(Context context) {
        super(context, null);
    }

    public void setSprinkleDensity(float density) {
        sprinkleTexture = getContext().graphicsUtil.createSprinkleTexture(8192, 8192, density);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(sprinkleTexture, getX(), getY());
    }
}
