package com.thecomet.spacerocks;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Action;

public abstract class PhysicsEntity extends Entity {
    private final static float WORLD_SCALE = 10;
    private final static float LINE_THICKNESS = 2;

    private final static int MASK_PLAYER = 0x01;
    private final static int MASK_ENTITY = 0x02;
    private final static int MASK_BULLET = 0x04;

    private Body body;

    public PhysicsEntity(Context context) {
        super(context);
    }

    protected void setupPhysics(BodyDef.BodyType bodyType) {
        createPhysicsFixtures(bodyType);
        createSynchronisationActions(bodyType);
    }

    protected abstract void configureFixture(FixtureDef fixtureDef);

    private void createPhysicsFixtures(BodyDef.BodyType bodyType) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(getX() / WORLD_SCALE, getY() / WORLD_SCALE);
        body = getContext().world.createBody(bodyDef);

        for (LineSoup.Group group : getLineSoup().getGroups().values()) {
            if (!group.physics) {
                continue;
            }
            for (LineSoup.Segment segment : group.segments) {
                Vector2 start = segment.start.cpy().sub(getOriginX(), getOriginY()).scl(1.0f / WORLD_SCALE);
                Vector2 end = segment.end.cpy().sub(getOriginX(), getOriginY()).scl(1.0f / WORLD_SCALE);
                Vector2 line = end.cpy().sub(start);
                Vector2 center = line.cpy().scl(0.5f).add(start);
                float len = line.len() * 0.5f;
                float angle = (new Vector2(1, 0)).angle(line);
                final float thickness = LINE_THICKNESS / WORLD_SCALE;

                PolygonShape shape = new PolygonShape();
                shape.setAsBox(len, thickness, center, angle * (float)Math.PI / 180.0f);

                FixtureDef fixtureDef = new FixtureDef();
                fixtureDef.shape = shape;
                configureFixture(fixtureDef);
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
