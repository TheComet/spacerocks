package com.thecomet.spacerocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;

public interface IGraphicsUtil {
    HashMap<String, TextureRegion> renderPixmapsFromLineSoup(LineSoup lineSoup);
    HashMap<String, TextureRegion> renderPixmapsFromLineSoup(LineSoup lineSoup, boolean doDrawBoundingBoxes);
    Texture createSprinkleTexture(int width, int height, float sprinkleDensity);
}
