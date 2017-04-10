package com.thecomet.spacerocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;

/**
 * Do absolutely nothing.
 */
public class HeadlessGraphicsUtil extends GraphicsUtil {

    /**
     * The callee expects the line groups to exist in the returned hash map. Create empty texture regions for each
     * group and insert them into the hash map.
     */
    @Override
    public HashMap<String, TextureRegion> renderPixmapsFromLineSoup(LineSoup lineSoup, boolean doDrawBoundingBoxes) {
        HashMap<String, TextureRegion> textureRegions = new HashMap<>();
        lineSoup.getGroups().forEach((name, group) -> {
            textureRegions.put(name, new TextureRegion());
        });
        return textureRegions;
    }

    @Override
    public Texture createSprinkleTexture(int width, int height, float sprinkleDensity) {
        return null;
    }
}
