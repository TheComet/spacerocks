package com.thecomet.spacerocks;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;

public abstract class AbstractGraphicsUtil implements IGraphicsUtil {
    @Override
    public HashMap<String, TextureRegion> renderPixmapsFromLineSoup(LineSoup lineSoup) {
        return renderPixmapsFromLineSoup(lineSoup, false);
    }
}
