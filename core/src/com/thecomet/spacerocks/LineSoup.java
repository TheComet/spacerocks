package com.thecomet.spacerocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;
import java.util.HashMap;

public class LineSoup {
    private HashMap<String, Group> groups = new HashMap<String, Group>();
    private Vector2 origin = new Vector2(0, 0);
    private Vector2 actionPoint = new Vector2(0, 0);
    private float aspectRatio;

    public static class Segment {
        public Vector2 start;
        public Vector2 end;
    }

    public static class Group {
        public ArrayList<Segment> segments = new ArrayList<Segment>();
    }

    public HashMap<String, Group> getGroups() {
        return groups;
    }

    public Vector2 getOrigin(float scaleInPixels) {
        return origin.cpy().scl(scaleInPixels);
    }

    public Vector2 getActionPoint(float scaleInPixels) {
        return actionPoint.cpy().scl(scaleInPixels);
    }

    public float getAspectRatio() {
        return aspectRatio;
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

                segment.start.scl(scale);
                segment.end.scl(scale);
            }
        }

        // Do same thing to point of origin and action point
        origin.x -= minx;
        origin.y -= miny;
        origin.scl(scale);
        actionPoint.x -= minx;
        actionPoint.y -= miny;
        actionPoint.scl(scale);
    }

    public static LineSoup load(String jsonFile) {
        Json json = new Json();
        LineSoup lineSoup = json.fromJson(LineSoup.class, Gdx.files.internal(jsonFile));
        lineSoup.normalise();
        return lineSoup;
    }
}
