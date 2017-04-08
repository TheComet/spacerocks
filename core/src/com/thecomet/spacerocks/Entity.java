package com.thecomet.spacerocks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;

public class Entity extends Actor implements Disposable {
    private Context context;
    private LineSoup lineSoup;
    private HashMap<String, TextureRegion> textureRegions;
    private Vector2 actionPoint;
    private boolean doDrawBoundingBoxes = false;

    public Entity(Context context) {
        this.context = context;
    }

    protected Context getContext() {
        return context;
    }

    protected LineSoup getLineSoup() {
        return lineSoup;
    }

    protected HashMap<String, TextureRegion> getTextureRegions() {
        return textureRegions;
    }

    public void setPosition(Vector2 position) {
        setPosition(position.x, position.y);
    }

    public Vector2 getDirection() {
        return new Vector2(0, 1).rotate(getRotation());
    }

    public Vector2 getActionPoint() {
        return actionPoint.cpy()
                .sub(getOriginX(), getOriginY())
                .rotate(getRotation())
                .add(getOriginX(), getOriginY())
                .add(getX(), getY());
    }

    /**
     * Useful for debugging pixmap related stuff.
     */
    public void drawBoundingBoxes(boolean enable) {
        doDrawBoundingBoxes = enable;
    }

    protected void loadLines(String linesFile, float scaleInPixels) {
        lineSoup = LineSoup.load(linesFile);
        lineSoup.rescaleLines(scaleInPixels - 1);
        textureRegions = context.graphicsUtil.renderPixmapsFromLineSoup(lineSoup, (int)scaleInPixels);

        Vector2 origin = lineSoup.getOrigin();
        setOrigin(origin.x, origin.y);

        actionPoint = lineSoup.getActionPoint();
    }

    protected void drawTextureRegion(Batch batch, TextureRegion textureRegion) {
        batch.draw(textureRegion, getX(), getY(), getOriginX(), getOriginY(),
                textureRegion.getRegionWidth(), textureRegion.getRegionHeight(),
                1, 1, getRotation());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        for (TextureRegion region : textureRegions.values()) {
            drawTextureRegion(batch, region);
        }
    }

    /**
     * When the actor gets removed from the stage, Box2D entities will still remain in the world. We have to remove
     * them by calling dispose(). Override the call to remove() and insert our cleanup code here.
     * @return
     */
    @Override
    public boolean remove() {
        dispose();
        return super.remove();
    }

    @Override
    public void dispose() {
        for (TextureRegion region : textureRegions.values()) {
            region.getTexture().dispose();
        }
    }
}
