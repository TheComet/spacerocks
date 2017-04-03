package com.thecomet.spacerocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;
import java.util.HashMap;

public class Lines {
    public HashMap<String, Group> groups = new HashMap<String, Group>();

    public static class Segment {
        public Vector2 start;
        public Vector2 end;
    }

    public static class Group {
        public ArrayList<Segment> segments = new ArrayList<Segment>();
    }

    public Texture[] renderToTextures(int scaleInPixels) {
        Texture textures[] = new Texture[groups.size()];

        int i = 0;
        for (Group group : groups.values()) {
            Pixmap pixmap = new Pixmap(64, 64, Pixmap.Format.RGBA8888);
            pixmap.setColor(Color.WHITE);
            pixmap.setFilter(Pixmap.Filter.BiLinear);
            for (Segment segment : group.segments) {
                pixmap.drawLine(
                        (int)segment.start.x, (int)segment.start.y,
                        (int)segment.end.x, (int)segment.end.y
                );
            }
            textures[i++] = new Texture(pixmap);
            pixmap.dispose();
        }

        return textures;
    }

    private void normalise() {
        // TODO
    }

    public static Lines load(String jsonFile) {
        Json json = new Json();
        Lines lines = json.fromJson(Lines.class, Gdx.files.internal(jsonFile));
        lines.normalise();
        return lines;
    }
}
