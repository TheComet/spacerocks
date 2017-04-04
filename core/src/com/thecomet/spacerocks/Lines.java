package com.thecomet.spacerocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Lines {
    public HashMap<String, Group> groups = new HashMap<String, Group>();

    private Vector2 origin;
    private float aspectRatio;

    public static class Segment {
        public Vector2 start;
        public Vector2 end;
    }

    public static class Group {
        public ArrayList<Segment> segments = new ArrayList<Segment>();
    }

    public HashMap<String, TextureRegion> renderToTextures(int scaleInPixels) {
        int width = scaleInPixels;
        int height = scaleInPixels;
        if (aspectRatio > 1.0f) {
            height /= aspectRatio;
        } else {
            width *= aspectRatio;
        }

        Pixmap.Format format = Pixmap.Format.RGBA8888;
        HashMap<String, TextureRegion> regions = new HashMap<>();
        Texture texture = new Texture(width * groups.size(), height, format);

        int i = 0;
        for (String groupKey : groups.keySet()) {
            Pixmap pixmap = new Pixmap(width, height, format);
            pixmap.setColor(Color.WHITE);
            pixmap.setFilter(Pixmap.Filter.BiLinear);
            pixmap.setBlending(Pixmap.Blending.None);

            for (Segment segment : groups.get(groupKey).segments) {
                pixmap.drawLine(
                        (int)(segment.start.x * scaleInPixels), height - (int)(segment.start.y * scaleInPixels),
                        (int)(segment.end.x * scaleInPixels), height - (int)(segment.end.y * scaleInPixels)
                );
            }

            texture.draw(pixmap, width * i, 0);
            regions.put(groupKey, new TextureRegion(texture, i * width, 0, width, height));

            pixmap.dispose();
            i++;
        }

        return regions;
    }

    public Vector2 calculateOrigin(float scaleInPixels) {
        return origin.cpy().scl(scaleInPixels);
    }

    private void normalise() {
        float minx = Float.MAX_VALUE;
        float maxx = -Float.MIN_VALUE;
        float miny = Float.MAX_VALUE;
        float maxy = -Float.MIN_VALUE;

        // Computes the bounding box
        for (Group group : groups.values()) {
            for (Segment segment : group.segments) {
                minx = segment.start.x < minx ? segment.start.x : minx;
                maxx = segment.start.x > maxx ? segment.start.x : maxx;
                miny = segment.start.y < miny ? segment.start.y : miny;
                maxy = segment.start.y > maxy ? segment.start.y : maxy;

                minx = segment.end.x < minx ? segment.end.x : minx;
                maxx = segment.end.x > maxx ? segment.end.x : maxx;
                miny = segment.end.y < miny ? segment.end.y : miny;
                maxy = segment.end.y > maxy ? segment.end.y : maxy;
            }
        }

        float lenx = maxx - minx;
        float leny = maxy - miny;
        float scale = lenx > leny ? lenx : leny;
        scale = 1.0f / scale;
        aspectRatio = lenx / leny;

        // Offset and scale to 1.0 so it fits into a [(0, 0) .. (1, 1)] box
        for (Group group : groups.values()) {
            for (Segment segment : group.segments) {
                segment.start.x -= minx;
                segment.start.y -= miny;
                segment.end.x -= minx;
                segment.end.y -= miny;

                segment.start.x *= scale;
                segment.start.y *= scale;
                segment.end.x *= scale;
                segment.end.y *= scale;
            }
        }

        // Do same thing to point of origin
        origin.x -= minx;
        origin.y -= miny;
        origin.x *= scale;
        origin.y *= scale;
    }

    public static Lines load(String jsonFile) {
        Json json = new Json();
        Lines lines = json.fromJson(Lines.class, Gdx.files.internal(jsonFile));
        lines.normalise();
        return lines;
    }
}
