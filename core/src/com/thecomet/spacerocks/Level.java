package com.thecomet.spacerocks;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Level extends PhysicsEntity {

    static Level createLevel(Context context) {
        Level level = new Level(context);
        context.stage.addActor(level);
        return level;
    }

    public Level(Context context) {
        super(context);
        loadLines("lines/level1.json", 1000);
        setupPhysics(BodyDef.BodyType.StaticBody);
    }

    @Override
    protected void configureFixture(FixtureDef fixtureDef) {
    }
}
