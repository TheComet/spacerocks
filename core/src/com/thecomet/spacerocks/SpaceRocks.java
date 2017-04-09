package com.thecomet.spacerocks;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class SpaceRocks extends ApplicationAdapter {
    protected Context context;
    private Box2DDebugRenderer debugRenderer;
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

    protected Context createContext() {
        Context context = new Context();
        context.world = new World(new Vector2(0, 0), true);
        context.stage = new Stage();
        context.graphicsUtil = new GraphicsUtil();
        return context;
    }

    protected void createViewport() {
        Camera camera = new OrthographicCamera();
        Viewport viewport = new ScreenViewport(camera);
        context.stage.setViewport(viewport);
    }

    protected void createRenderers() {
        debugRenderer = new Box2DDebugRenderer();
    }

    protected void createEntities() {

        Asteroid asteroid = Asteroid.createAsteroid(context, Asteroid.Type.HUGE);
        asteroid.setPosition(200, 200);
        asteroid.setLinearVelocity(Util.getRandomVelocity(5, 20));
        asteroid.setAngularVelocity(MathUtils.random(-100, 100));
        context.stage.addActor(asteroid);

        Ship ship = new Ship(context);
        ship.setPosition(50, 50);
        ship.setCamera(context.stage.getCamera());
        context.stage.setKeyboardFocus(ship);
        context.stage.addActor(ship);

        Level.createLevel(context);
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
        stepPhysics(delta);
        preDraw();
        draw();

        if (drawDebug) {
            debugRenderer.render(context.world, context.stage.getCamera().combined.scl(PhysicsEntity.WORLD_SCALE));
        }
    }

    private void stepPhysics(float delta) {
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

    private void preDraw() {
        context.stage.getRoot().fire(new PreDrawEvent());
    }

    protected void draw() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        context.stage.draw();
    }
    
    @Override
    public void dispose() {
        context.dispose();
        debugRenderer.dispose();
    }
}
