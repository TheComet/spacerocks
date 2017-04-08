package com.thecomet.spacerocks;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HeadlessStage extends Stage {
    public HeadlessStage() {
        super(new HeadlessViewport(), new HeadlessBatch());
    }

    public HeadlessStage(Viewport viewport) {
        super(viewport, new HeadlessBatch());
    }
}
