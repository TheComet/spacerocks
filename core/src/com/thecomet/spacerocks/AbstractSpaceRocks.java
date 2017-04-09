package com.thecomet.spacerocks;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public abstract class AbstractSpaceRocks extends ApplicationAdapter {
    protected Context context;
    private boolean drawDebug = false;

    private float physicsTimeAccumulator = 0;
    private final static float PHYSICS_DT = 1.0f / 45;

    @Override
    public void create() {
        context = createContext();
        createViewport();
        createRenderers();
        createEntities();

        Gdx.input.setInputProcessor(context.stage);

        context.stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.F1) {
                    drawDebug = !drawDebug;
                    return true;
                }
                return false;
            }
        });
    }

    protected abstract Context createContext();
    protected abstract void createViewport();
    protected abstract void createRenderers();
    protected abstract void preDraw();
    protected abstract void draw();
    protected abstract void drawDebug();

    private void createEntities() {
        AsteroidGenerator generator = new AsteroidGenerator(context);
        Asteroid asteroid;

        for (int i = 0; i < 10; i++) {
            asteroid = generator.generateAsteroid();
            asteroid.setPosition(300, 300);
            asteroid.setLinearVelocity(Util.getRandomVelocity(5, 20));
            asteroid.setAngularVelocity(MathUtils.random(-100, 100));
            context.stage.addActor(asteroid);
        }

        Ship ship = new Ship(context);
        ship.setPosition(50, 50);
        ship.setCamera(context.stage.getCamera());
        context.stage.setKeyboardFocus(ship);
        context.stage.addActor(ship);

        Level level = new Level(context);
        context.stage.addActor(level);

        BackgroundSprinkles backgroundSprinkles = new BackgroundSprinkles(context);
        backgroundSprinkles.setSprinkleDensity(0.001f);
        context.stage.addActor(backgroundSprinkles);
    }

    @Override
    public void resize(int width, int height) {
        context.stage.getViewport().update(width, height);
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        stepLogic(delta);
        _stepPhysics(delta);
        preDraw();
        draw();
        drawDebug();

    }

    private void _stepPhysics(float delta) {
        // max frame time to avoid spiral of death (on slow devices)
        float frameTime = Math.min(delta, 0.25f);

        // fixed time step, as explained here http://gafferongames.com/game-physics/fix-your-timestep/
        physicsTimeAccumulator += frameTime;
        while (physicsTimeAccumulator >= PHYSICS_DT) {
            context.world.step(PHYSICS_DT, 6, 2);
            physicsTimeAccumulator -= PHYSICS_DT;
        }
    }

    private void stepLogic(float delta) {
        context.stage.act(delta);
    }

    @Override
    public void dispose() {
        context.dispose();
    }
}
