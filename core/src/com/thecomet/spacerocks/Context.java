package com.thecomet.spacerocks;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;

public class Context implements Disposable {
    public Stage stage;
    public World world;
    public IGraphicsUtil graphicsUtil;
    public Client client;
    public Server server;

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
    }
}
