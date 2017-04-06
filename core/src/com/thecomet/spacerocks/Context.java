package com.thecomet.spacerocks;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;

public class Context implements Disposable {
    public Stage stage;
    public World world;

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
    }
}
