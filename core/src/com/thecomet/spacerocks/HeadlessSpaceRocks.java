package com.thecomet.spacerocks;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class HeadlessSpaceRocks extends SpaceRocks {
    @Override
    protected Context createContext() {
        Context context = new Context();
        context.stage = new HeadlessStage();
        context.world = new World(new Vector2(0, 0), true);
        context.graphicsUtil = new HeadlessGraphicsUtil();
        return context;
    }

    @Override
    protected void createRenderers() {
    }

    @Override
    protected void draw() {
    }
}
