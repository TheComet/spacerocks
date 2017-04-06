package com.thecomet.spacerocks;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;

public class Bullet extends PhysicsEntity {
    private static final float VELOCITY = 900;

    public static Bullet createBullet(Context context, Vector2 position, Vector2 direction) {
        Bullet bullet = new Bullet(context);
        context.stage.addActor(bullet);
        bullet.setPosition(position);
        bullet.setLinearVelocity(direction.cpy().scl(VELOCITY));
        return bullet;
    }

    public Bullet(Context context) {
        super(context);

        loadLines("lines/bullet.json", 12);
        setupPhysics(BodyDef.BodyType.DynamicBody);
    }
}
