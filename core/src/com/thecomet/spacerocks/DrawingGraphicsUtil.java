package com.thecomet.spacerocks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.awt.*;
import java.util.HashMap;

public class DrawingGraphicsUtil extends GraphicsUtil {

    @Override
    public HashMap<String, TextureRegion> renderPixmapsFromLineSoup(LineSoup lineSoup, boolean doDrawBoundingBoxes) {
        // Depending on whether width or height is larger, scale one or the other using the aspect ratio. The result is
        // such that neither the width nor the height will exceed "scaleInPixels" if they are different.
        int width = (int) lineSoup.getScaleInPixels();
        int height = (int) lineSoup.getScaleInPixels();

        if (lineSoup.getAspectRatio() > 1.0f) {
            height = (int) Math.ceil(lineSoup.getAspectRatio() * height);
        } else {
            width = (int) Math.ceil(lineSoup.getAspectRatio() * width);
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

    @Override
    public Texture createSprinkleTexture(int width, int height, float sprinkleDensity) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA4444);
        pixmap.setColor(new Color(1, 1, 1, 0.5f));
        int numberOfSprinkles = (int)(width * height * sprinkleDensity);

        for (int i = 0; i < numberOfSprinkles; i++) {
            pixmap.drawPixel(
                    (int)(Math.random() * (width - 1)),
                    (int)(Math.random() * (height - 1))
            );
        }

        return new Texture(pixmap);
    }
}
