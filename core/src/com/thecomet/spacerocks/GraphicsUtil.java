package com.thecomet.spacerocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;

public abstract class GraphicsUtil {
    public abstract HashMap<String, TextureRegion> renderPixmapsFromLineSoup(LineSoup lineSoup, boolean doDrawBoundingBoxes);
    public abstract Texture createSprinkleTexture(int width, int height, float sprinkleDensity);

    public HashMap<String, TextureRegion> renderPixmapsFromLineSoup(LineSoup lineSoup) {
        return renderPixmapsFromLineSoup(lineSoup, false);
    }
}
