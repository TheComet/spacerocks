package com.thecomet.spacerocks;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SpaceRocks extends ApplicationAdapter {
    private Stage stage;
    private World world;

    private float physicsTimeAccumulator = 0;
    private final static float PHYSICS_DT = 1.0f / 45;

    public Ship createShip() {
        Ship ship = new Ship(this);
        stage.addActor(ship);
        stage.setKeyboardFocus(ship);
        return ship;
    }

    public Bullet createBullet() {
        Bullet bullet = new Bullet(this);
        stage.addActor(bullet);
        return bullet;
    }

    @Override
    public void create() {
        stage = new Stage(new ScreenViewport());
        stage.getViewport().setScreenSize(1366, 768);
        Gdx.input.setInputProcessor(stage);

        stage.addActor(Asteroid.createAsteroid(Asteroid.AsteroidClass.HUGE));
        createShip();

        world = new World(new Vector2(0, 0), true);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        stepPhysics(delta);
        stepLogic(delta);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    private void stepPhysics(float delta) {
        // max frame time to avoid spiral of death (on slow devices)
        float frameTime = Math.min(delta, 0.25f);

        // fixed time step, as explained here http://gafferongames.com/game-physics/fix-your-timestep/
        physicsTimeAccumulator += frameTime;
        while (physicsTimeAccumulator >= PHYSICS_DT) {
            world.step(PHYSICS_DT, 6, 2);
            physicsTimeAccumulator -= PHYSICS_DT;
        }
    }

    private void stepLogic(float delta) {
        stage.act(delta);
    }
    
    @Override
    public void dispose() {
        stage.dispose();
    }
}
