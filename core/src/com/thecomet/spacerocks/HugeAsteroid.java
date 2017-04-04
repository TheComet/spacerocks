package com.thecomet.spacerocks;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;

public class HugeAsteroid extends Asteroid {
    private final TextureRegion textureRegion;

    public HugeAsteroid() {
        createActions();

        loadLines("lines/huge_asteroid.json", 80);
        textureRegion = textureRegions.get("asteroid");
        setPosition(100, 100);
    }

    private void createActions() {
        addAction(new Action() {
            @Override
            public boolean act(float delta) {
                setRotation(getRotation() + delta * getTurnSpeed());
                Vector2 movement = new Vector2(getVelocity()).scl(delta);
                setPosition(getPosition().add(movement));
                return false;
            }
        });
    }

    private Vector2 getPosition() {
        return new Vector2(getX(), getY());
    }

    private void setPosition(Vector2 position) {
        setPosition(position.x, position.y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        drawTextureRegion(batch, textureRegion);
    }
}
