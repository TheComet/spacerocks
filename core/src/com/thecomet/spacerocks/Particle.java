package com.thecomet.spacerocks;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.scenes.scene2d.Action;

public class Particle extends PhysicsEntity {
    private float life;

    public static void createExplosion(Context context, Vector2 position, float velocity, int particleCount) {
        for (int i = 0; i < particleCount; i++) {
            Particle particle = new Particle(context);
            particle.setPosition(position);
            particle.setLinearVelocity(Util.getRandomVelocity(0, velocity));
            particle.setLife(MathUtils.random(0.2f, 1.0f));
            context.stage.addActor(particle);
        }
    }

    public Particle(Context context) {
        super(context, LineSoup.load("lines/particle.json").cookSoup(1.2f));
        setAngularVelocity(MathUtils.random(-100, 100));

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

    public void setLife(float life) {
        this.life = life;
    }

    @Override
    protected void configureBody(BodyDef bodyDef) {
        bodyDef.type = BodyDef.BodyType.DynamicBody;
    }

    @Override
    protected void configureFixture(FixtureDef fixtureDef) {
        fixtureDef.friction = 0.0f;
        fixtureDef.density = 1f;
        fixtureDef.filter.categoryBits = MASK_PARTICLE;
        fixtureDef.filter.maskBits = ~MASK_PARTICLE;
    }
}
