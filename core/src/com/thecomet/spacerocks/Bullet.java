package com.thecomet.spacerocks;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.scenes.scene2d.Action;

public class Bullet extends PhysicsEntity {
    private static final float VELOCITY = 800;
    private float life = 1.4f;

    public static Bullet createBullet(Context context, Vector2 position, Vector2 direction) {
        Bullet bullet = new Bullet(context);
        context.stage.addActor(bullet);
        bullet.setPosition(position.sub(bullet.getOriginX(), bullet.getOriginY()));
        bullet.setLinearVelocity(direction.cpy().scl(VELOCITY));
        return bullet;
    }

    public Bullet(Context context) {
        super(context);

        loadLines("lines/bullet.json", 5);
        setupPhysics(BodyDef.BodyType.DynamicBody);

        addAction(new Action() {
            @Override
            public boolean act(float delta) {
                life -= delta;
                if (life <= 0) {
                    remove();
                }
                return false;
            }
        });
    }

    @Override
    protected void configureFixture(FixtureDef fixtureDef) {
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;
        fixtureDef.filter.categoryBits = PhysicsEntity.MASK_BULLET;
    }
}
