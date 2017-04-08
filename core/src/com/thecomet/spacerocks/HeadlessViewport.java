package com.thecomet.spacerocks;

import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * The following Viewport methods need to be overridden because they make GL calls which aren't available in headless
 * mode.
 */
public class HeadlessViewport extends Viewport {

    @Override
    public void apply (boolean centerCamera) {

    }
}
