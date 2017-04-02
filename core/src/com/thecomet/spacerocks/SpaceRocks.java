package com.thecomet.spacerocks;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

public class SpaceRocks extends ApplicationAdapter {
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    private ArrayList<Entity> entities = new ArrayList<Entity>();
    private ArrayList<Entity> temporaryEntities = new ArrayList<Entity>();

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        createShip();
    }

    private void createShip() {
        Ship ship = new Ship(this);
        ship.create();
        entities.add(ship);
    }

    @Override
    public void render() {
        for (Entity entity : entities) {
            entity.update(1.0f/60);
        }

        entities.addAll(temporaryEntities);
        temporaryEntities.clear();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        for (Entity entity : entities) {
            entity.render(batch, shapeRenderer);
        }

        shapeRenderer.end();
        batch.end();
    }
    
    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        shapeRenderer = null;

        for (Entity entity : entities) {
            entity.dispose();
        }
    }

    public void addEntity(Entity entity) {
        temporaryEntities.add(entity);
    }
}
