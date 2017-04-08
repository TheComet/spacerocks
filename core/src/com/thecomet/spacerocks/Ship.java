package com.thecomet.spacerocks;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.scenes.scene2d.Action;

import javax.xml.ws.Endpoint;

public class Ship extends PhysicsEntity {
    private TextureRegion shipTextureRegion;
    private TextureRegion exhaustTextureRegion;
    private ShipControls controls;

    private static final float ROTATION_SPEED = 200;
    private static final float ACCELERATION = 200;
    private static final float MAX_VELOCITY = 200;
    private static final float RECOIL_FORCE = 0.0002f;

    private float exhaustTimer = 0;
    private boolean doDrawExhaust = false;

    private Vector2 velocity = new Vector2();

    public static Ship createLocalPlayer(Context context) {
        Ship ship = new Ship(context);
        context.stage.addActor(ship);
        context.stage.setKeyboardFocus(ship);
        return ship;
    }

    public static Ship createNetworkedPlayer(Context context) {
        // TODO ???
        return null;
    }

    public static Ship createAIPlayer(Context context) {
        // TODO ???
        return null;
    }

    public Ship(Context context) {
        super(context);

        setupControls();
        createActions();

        loadLines("lines/ship.json", 32);
        shipTextureRegion = getTextureRegions().get("ship");
        exhaustTextureRegion = getTextureRegions().get("exhaust");

        setupPhysics(BodyDef.BodyType.DynamicBody);
    }

    @Override
    protected void configureFixture(FixtureDef fixtureDef) {
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;
        fixtureDef.filter.categoryBits = PhysicsEntity.MASK_PLAYER;
        fixtureDef.filter.maskBits = ~PhysicsEntity.MASK_BULLET;
    }

    private void setupControls() {
        // Input mapper implements the listener interface, so add it as a listener to this actor so it receives input
        controls = new ShipControls();
        ShipInputMapper inputMapper = new ShipInputMapper(controls);
        addListener(inputMapper);
    }

    private void createActions() {
        addAction(new Action() {
            @Override
            public boolean act(float delta) {
                // Advance edge detection logic in input mapper
                controls.nextFrame();

                // Update ship movement and other behaviour
                processShooting();
                updateRotation(delta);
                updateVelocity(delta);
                return false;
            }
        });
    }

    private void processShooting() {
        if (controls.getShoot()) {
            Vector2 direction = getDirection();
            Bullet.createBullet(getContext(), getActionPoint(), direction);
            velocity.mulAdd(direction, -RECOIL_FORCE);
        }
    }

    private void updateRotation(float timeStep) {
        if (controls.getTurnLeft()) {
            setRotation(getRotation() + timeStep * ROTATION_SPEED);
        } else if (controls.getTurnRight()) {
            setRotation(getRotation() - timeStep * ROTATION_SPEED);
        }
    }

    private void updateVelocity(float timeStep) {
        if (controls.getAccelerate()) {
            Vector2 direction = getDirection();
            velocity.mulAdd(direction, timeStep * ACCELERATION);
            updateExhaustFlicker(timeStep, false);
        } else {
            updateExhaustFlicker(timeStep, true);
        }

        velocity.clamp(0, MAX_VELOCITY);
        setLinearVelocity(velocity);
    }

    private void updateExhaustFlicker(float timeStep, boolean reset) {
        if (reset) {
            doDrawExhaust = false;
            return;
        }

        exhaustTimer += timeStep;
        doDrawExhaust = exhaustTimer >= 0.07;
        if (exhaustTimer >= 0.14) {
            exhaustTimer = 0;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        drawTextureRegion(batch, shipTextureRegion);
        if (doDrawExhaust) {
            drawTextureRegion(batch, exhaustTextureRegion);
        }
    }
}
