package com.thecomet.spacerocks;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
    protected Vector2 position = new Vector2(0, 0);
    protected float rotation = 0;

    public abstract void create();
    public abstract  void dispose();
    public abstract void render(Batch batch, ShapeRenderer shapeRenderer);
    public abstract void update(float timeStep);
}
