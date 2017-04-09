package com.thecomet.spacerocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LineSoup {
    private HashMap<String, Group> groups = new HashMap<String, Group>();
    private Vector2 origin = new Vector2(0, 0);
    private Vector2 actionPoint = new Vector2(0, 0);
    private float aspectRatio;
    private float scaleInPixels;

    private static HashMap<String, LineSoup> lineSoupCache = new HashMap<>();

    private LineSoup(LineSoup other, float scaleInPixels) {
        HashMap<String, Group> groupsCopy = new HashMap<>();
        for (Map.Entry<String, Group> stringGroupEntry : other.groups.entrySet()) {
            groupsCopy.put(stringGroupEntry.getKey(), stringGroupEntry.getValue().cpy());
        }
        this.groups = groupsCopy;

        this.origin = other.origin.cpy();
        this.actionPoint = other.actionPoint.cpy();
        this.aspectRatio = other.aspectRatio;

        this.scaleInPixels = scaleInPixels;
        rescaleLines(scaleInPixels);
    }

    public LineSoup() {}

    public float getScaleInPixels() {
        return scaleInPixels;
    }

    public static class Segment {
        public Vector2 start;
        public Vector2 end;

        public Segment() {}

        public Segment(Vector2 start, Vector2 end) {
            this.start = start;
            this.end = end;
        }

        Segment cpy() {
            return new Segment(this.start.cpy(), this.end.cpy());
        }
    }

    public static class Group {
        public boolean physics = true;
        public ArrayList<Segment> segments = new ArrayList<Segment>();

        public Group() {}

        public Group(boolean physics, ArrayList<Segment> segments) {
            this.physics = physics;
            this.segments = segments;
        }

        Group cpy() {
            ArrayList<Segment> segmentsCopy = new ArrayList<>();
            for (Segment segment : segments) {
                segmentsCopy.add(segment.cpy());
            }

            return new Group(physics, segmentsCopy);
        }
    }

    public HashMap<String, Group> getGroups() {
        return groups;
    }

    public Vector2 getOrigin() {
        return origin;
    }

    public Vector2 getActionPoint() {
        return actionPoint;
    }

    public float getAspectRatio() {
        return aspectRatio;
    }

    private void rescaleLines(float scale) {
        // Pixmaps range from 0 to scale - 1, but scale is in pixels
        scale -= 1;

        float minx = Float.MAX_VALUE;
        float maxx = -Float.MAX_VALUE;
        float miny = Float.MAX_VALUE;
        float maxy = -Float.MAX_VALUE;

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
        float den = lenx > leny ? lenx : leny;
        den = scale / den;
        aspectRatio = lenx / leny;

        // Offset and scale to 1.0 so it fits into a [(0, 0) .. (1, 1)] box
        for (Group group : groups.values()) {
            for (Segment segment : group.segments) {
                segment.start.x -= minx;
                segment.start.y -= miny;
                segment.end.x -= minx;
                segment.end.y -= miny;

                segment.start.scl(den);
                segment.end.scl(den);
            }
        }

        // Do same thing to point of origin and action point
        origin.x -= minx;
        origin.y -= miny;
        origin.scl(den);
        actionPoint.x -= minx;
        actionPoint.y -= miny;
        actionPoint.scl(den);
    }

    public static LineSoup load(String jsonFile) {
        if (lineSoupCache.containsKey(jsonFile)) {
            return lineSoupCache.get(jsonFile);
        } else {
            LineSoup lineSoup = new Json().fromJson(LineSoup.class, Gdx.files.internal(jsonFile));
            lineSoupCache.put(jsonFile, lineSoup);
            return lineSoup;
        }
    }

    public LineSoup cookSoup(float scaleInPixels) {
        return new LineSoup(this, scaleInPixels);
    }
}
