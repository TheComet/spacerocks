package com.thecomet.spacerocks;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Context implements Disposable {
    public Stage stage;
    public World world;

    public Context() {
        stage = new Stage(new ScreenViewport());
        world = new World(new Vector2(0, 0), true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
    }
}
