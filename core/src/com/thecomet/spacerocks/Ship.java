package com.thecomet.spacerocks;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by thecomet on 02/04/17.
 */
public class Ship extends Entity {
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    @Override
    public void render(Batch batch) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 1, 1, 1);
        shapeRenderer.end();
    }

    @Override
    public void update(float timeStep) {

    }
}
