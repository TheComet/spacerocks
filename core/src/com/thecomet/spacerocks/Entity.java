package com.thecomet.spacerocks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;

public class Entity extends Actor implements Disposable {
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
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
        textureRegions = renderPixmaps((int)scaleInPixels);

        Vector2 origin = lineSoup.getOrigin();
        setOrigin(origin.x, origin.y);

        actionPoint = lineSoup.getActionPoint();
    }

    private HashMap<String, TextureRegion> renderPixmaps(int scaleInPixels) {
        // Depending on whether width or height is larger, scale one or the other using the aspect ratio. The result is
        // such that neither the width nor the height will exceed "scaleInPixels" if they are different.
        int width = scaleInPixels;
        int height = scaleInPixels;
        if (lineSoup.getAspectRatio() > 1.0f) {
            height /= lineSoup.getAspectRatio();
        } else {
            width *= lineSoup.getAspectRatio();
        }

        Pixmap.Format format = Pixmap.Format.RGBA8888;
        HashMap<String, LineSoup.Group> groups = lineSoup.getGroups();
        HashMap<String, TextureRegion> regions = new HashMap<>();
        Texture texture = new Texture(width * groups.size(), height, format);

        int i = 0;
        for (String groupKey : groups.keySet()) {
            Pixmap pixmap = new Pixmap(width, height, format);
            if (doDrawBoundingBoxes) {
                pixmap.setColor(Color.RED);
                pixmap.drawRectangle(0, 0, width, height);
            }
            pixmap.setColor(Color.WHITE);
            pixmap.setFilter(Pixmap.Filter.BiLinear);
            pixmap.setBlending(Pixmap.Blending.None);

            for (LineSoup.Segment segment : groups.get(groupKey).segments) {
                // Need to flip Y axis to match the GL coordinate system. Also note off by one fix
                pixmap.drawLine(
                        (int)segment.start.x, height - 1 - (int)segment.start.y,
                        (int)segment.end.x, height - 1 - (int)segment.end.y
                );
            }

            texture.draw(pixmap, width * i, 0);
            regions.put(groupKey, new TextureRegion(texture, i * width, 0, width, height));

            pixmap.dispose();
            i++;
        }

        return regions;
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
        batch.end();

        // TODO remove this, it's debug stuff
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setProjectionMatrix(getContext().stage.getCamera().combined);
        shapeRenderer.setColor(1, 0, 0, 1);
        Vector2 p = getActionPoint();
        shapeRenderer.circle(p.x, p.y, 3);
        p = new Vector2(getX(), getY());
        shapeRenderer.setColor(0, 1, 0, 1);
        shapeRenderer.circle(p.x, p.y, 3);
        p = new Vector2(getX() + getOriginX(), getY() + getOriginY());
        shapeRenderer.setColor(0, 0.4f, 1, 1);
        shapeRenderer.circle(p.x, p.y, 3);
        shapeRenderer.end();
        batch.begin();
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
        shapeRenderer.dispose();
    }
}
