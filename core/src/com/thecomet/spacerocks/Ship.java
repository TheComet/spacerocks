package com.thecomet.spacerocks;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Ship extends Entity {
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        shapeRenderer = null;
        camera = null;
    }

    @Override
    public void render(Batch batch) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 1, 1, 1);
        shapeRenderer.identity();
        shapeRenderer.rotate(0, 0, 1, rotation);
        shapeRenderer.translate(200, 200, 0);
        shapeRenderer.line(-10, -20, 0, 13);
        shapeRenderer.line(10f, -20, 0, 13);
        shapeRenderer.line(-8, -13.5f, 8, -13.5f);
        shapeRenderer.end();
    }

    @Override
    public void update(float timeStep) {

    }
}
