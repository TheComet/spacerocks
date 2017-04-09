package com.thecomet.spacerocks;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

public class HeadlessSpaceRocks extends AbstractSpaceRocks {
    @Override
    protected Context createContext() {
        Context context = new Context();
        context.stage = new HeadlessStage();
        context.world = new World(new Vector2(0, 0), true);
        context.graphicsUtil = new HeadlessGraphicsUtil();
        context.server = new Server();
        return context;
    }

    @Override
    protected void createViewport() {
    }

    @Override
    protected void createRenderers() {

    }

    @Override
    protected void setupNetworking() {
        context.server.start();
        try {
            context.server.bind(8234, 11434);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void stepLogic(float delta) {
        context.stage.act(delta);
    }

    @Override
    protected void stepPhysics(float delta) {
        context.world.step(delta, 6, 2);
    }

    @Override
    protected void stepNetwork(float delta) {

    }

    @Override
    protected void preDraw() {
    }

    @Override
    protected void draw() {
    }

    @Override
    protected void drawDebug() {
    }
}
