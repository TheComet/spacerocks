package com.thecomet.spacerocks;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.scenes.scene2d.Action;

public class EntityPhysics extends Entity {
    private Body body;

    public EntityPhysics(Context context) {
        super(context);
    }

    public Body getBody() {
        return body;
    }

    protected void setupPhysics(BodyDef.BodyType bodyType) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(getX(), getY());
        body = getContext().getWorld().createBody(bodyDef);

        for (LineSoup.Group group : getLineSoup().getGroups().values()) {
            if (!group.physics) {
                continue;
            }
            for (LineSoup.Segment segment : group.segments) {
                EdgeShape shape = new EdgeShape();
                shape.set(segment.start, segment.end);
                FixtureDef fixtureDef = new FixtureDef();
                fixtureDef.shape = shape;
                fixtureDef.density = 0.5f;
                fixtureDef.friction = 0.4f;
                fixtureDef.restitution = 0.6f;
                body.createFixture(fixtureDef);
                shape.dispose();
            }
        }
/*
        addAction(new Action() {
            @Override
            public boolean act(float delta) {
                setPosition(body.getPosition());
                setRotation(body.getAngle() * 180.0f / (float)Math.PI);
                return false;
            }
        });*/
    }
/*
    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        body.setTransform(x, y, body.getAngle() * (float)Math.PI / 180.0f);
    }

    @Override
    public void setRotation(float angle) {
        super.setRotation(angle);
        body.setTransform(body.getPosition().x, body.getPosition().y, body.getAngle() * (float)Math.PI / 180.0f);
    }*/
}
