package com.thecomet.spacerocks;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Action;

public class PhysicsEntity extends Entity {
    private final static float WORLD_SCALE = 10.0f;
    private Body body;

    public PhysicsEntity(Context context) {
        super(context);
    }

    protected void setupPhysics(BodyDef.BodyType bodyType) {
        createPhysicsFixtures(bodyType);
        createSynchronisationActions(bodyType);
    }

    private void createPhysicsFixtures(BodyDef.BodyType bodyType) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(getX() / WORLD_SCALE, getY() / WORLD_SCALE);
        body = getContext().world.createBody(bodyDef);

        MassData massData = new MassData();
        massData.mass = 5;
        body.setMassData(massData);

        for (LineSoup.Group group : getLineSoup().getGroups().values()) {
            if (!group.physics) {
                continue;
            }
            for (LineSoup.Segment segment : group.segments) {
                EdgeShape shape = new EdgeShape();
                shape.set(segment.start.cpy().sub(getOriginX(), getOriginY()).scl(1.0f / WORLD_SCALE),
                          segment.end.cpy().sub(getOriginX(), getOriginY()).scl(1.0f / WORLD_SCALE));

                FixtureDef fixtureDef = new FixtureDef();
                fixtureDef.shape = shape;
                fixtureDef.density = 0.5f;
                fixtureDef.friction = 0.4f;
                fixtureDef.restitution = 0.6f;
                body.createFixture(fixtureDef);

                shape.dispose();
            }
        }
    }

    private void createSynchronisationActions(BodyDef.BodyType bodyType) {
        // If the body is dynamic, then let Box2D take control of the actor's transform. Anything else and the actor
        // should instead be in control.
        if (bodyType.getValue() == BodyDef.BodyType.DynamicBody.getValue()) {
            addAction(new Action() {
                @Override
                public boolean act(float delta) {
                    setActorPosition(body.getPosition());
                    setActorRotation(body.getAngle());
                    return false;
                }
            });
        } else {
            addAction(new Action() {
                @Override
                public boolean act(float delta) {
                    setBodyPosition(getX(), getY());
                    setBodyRotation(getRotation());
                    return false;
                }
            });
        }
    }

    @Override
    public void setPosition(Vector2 position) {
        super.setPosition(position);
        setBodyPosition(position.x, position.y);
    }

    @Override
    public void setRotation(float angle) {
        super.setRotation(angle);
        setBodyRotation(angle);
    }

    public void setLinearVelocity(Vector2 velocity) {
        body.setLinearVelocity(velocity.scl(1.0f / WORLD_SCALE));
    }

    public void setAngularVelocity(float omega) {
        body.setAngularVelocity(omega * (float)Math.PI / 180.0f);
    }

    private void setBodyPosition(float x, float y) {
        body.setTransform(
                (x + getOriginX()) / WORLD_SCALE,
                (y + getOriginY()) / WORLD_SCALE,
                body.getAngle());
    }

    private void setBodyRotation(float angle) {
        Vector2 pos = body.getPosition();
        body.setTransform(pos.x, pos.y, angle * (float)Math.PI / 180.0f);
    }

    private void setActorPosition(Vector2 position) {
        Entity entity = PhysicsEntity.this;
        entity.setPosition(
                (position.x * WORLD_SCALE) - getOriginX(),
                (position.y * WORLD_SCALE) - getOriginY());
    }

    private void setActorRotation(float angle) {
        Entity entity = PhysicsEntity.this;
        entity.setRotation(angle * 180.0f / (float)Math.PI);
    }
}
