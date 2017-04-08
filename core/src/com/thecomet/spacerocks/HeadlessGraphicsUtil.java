package com.thecomet.spacerocks;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;

/**
 * Do absolutely nothing.
 */
public class HeadlessGraphicsUtil extends AbstractGraphicsUtil {
    @Override
    public HashMap<String, TextureRegion> renderPixmapsFromLineSoup(LineSoup lineSoup, int scaleInPixels, boolean doDrawBoundingBoxes) {
        return null;
    }
}
