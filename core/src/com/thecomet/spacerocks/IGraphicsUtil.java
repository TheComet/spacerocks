package com.thecomet.spacerocks;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;

public interface IGraphicsUtil {
    HashMap<String, TextureRegion> renderPixmapsFromLineSoup(LineSoup lineSoup, int scaleInPixels);
    HashMap<String, TextureRegion> renderPixmapsFromLineSoup(LineSoup lineSoup, int scaleInPixels, boolean doDrawBoundingBoxes);
}