package com.thecomet.spacerocks;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.utils.Disposable;

public abstract class PhysicsEntity extends Entity implements Disposable {
    public final static float WORLD_SCALE = 50;
    private final static float LINE_THICKNESS = 3;

    public final static short MASK_PLAYER = 0x01;
    public final static short MASK_ENTITY = 0x02;
    public final static short MASK_BULLET = 0x04;
    public final static short MASK_PARTICLE = 0x08;

    private Body body;

    public PhysicsEntity(Context context) {
        super(context);
    }

    protected abstract void configureBody(BodyDef bodyDef);
    protected abstract void configureFixture(FixtureDef fixtureDef);

    protected void setupPhysics() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX() / WORLD_SCALE, getY() / WORLD_SCALE);
        configureBody(bodyDef);
        body = getContext().world.createBody(bodyDef);

        for (LineSoup.Group group : getLineSoup().getGroups().values()) {
            if (!group.physics) {
                continue;
            }
            for (LineSoup.Segment segment : group.segments) {
                Vector2 start = segment.start.cpy().sub(getLineSoup().getOrigin()).scl(1.0f / WORLD_SCALE);
                Vector2 end = segment.end.cpy().sub(getLineSoup().getOrigin()).scl(1.0f / WORLD_SCALE);
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

        createSynchronisationActions(bodyDef.type);
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
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        setBodyPosition(x, y);
    }

    @Override
    public void setRotation(float angle) {
        super.setRotation(angle);
        setBodyRotation(angle);
    }

    public Vector2 getLinearVelocity() {
        return body.getLinearVelocity();
    }

    public void setLinearVelocity(Vector2 velocity) {
        body.setLinearVelocity(velocity.cpy().scl(1.0f / WORLD_SCALE));
    }

    public float getAngularVelocity() {
        return body.getAngularVelocity();
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


    @Override
    public void dispose() {
        super.dispose();
        getContext().world.destroyBody(body);
    }
}
