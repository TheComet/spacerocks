package com.thecomet.spacerocks;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Level extends PhysicsEntity {
    public Level(Context context) {
        super(context, LineSoup.load("lines/level1.json").cookSoup(1000));
    }

    @Override
    protected void configureBody(BodyDef bodyDef) {
        bodyDef.type = BodyDef.BodyType.StaticBody;
    }

    @Override
    protected void configureFixture(FixtureDef fixtureDef) {
    }
}
