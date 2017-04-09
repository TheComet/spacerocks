package com.thecomet.spacerocks;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class AsteroidGenerator {
    static float MIN_ANGLE = 10f;
    static float MAX_ANGLE = 40f;
    static float MIN_DISTANCE = 10f;
    static float MAX_DISTANCE = 40f;

    private final Context context;

    AsteroidGenerator(Context context) {
        this.context = context;
    }

    public Asteroid generateAsteroid() {
        return new ProcedurallyGeneratedAsteroid(context, generateLineSoup());
    }

    private LineSoup generateLineSoup() {
        return new ProcedurallyGeneratedLineSoup();
    }
}


class ProcedurallyGeneratedLineSoup extends LineSoup {
    public ProcedurallyGeneratedLineSoup() {
        Group group = new Group();

        float currentAngle = 0;
        ArrayList<Vector2> points = new ArrayList<>();

        while(currentAngle < 360) {
            float angleDifference = MathUtils.random(AsteroidGenerator.MIN_ANGLE, AsteroidGenerator.MAX_ANGLE);
            float distance = MathUtils.random(AsteroidGenerator.MIN_DISTANCE, AsteroidGenerator.MAX_DISTANCE);
            currentAngle += angleDifference;

            Vector2 newPoint = new Vector2(1,1);
            newPoint.setAngle(currentAngle);
            newPoint.setLength(distance);
            points.add(newPoint);
        }

        addSegments(group, points);

        getGroups().put("asteroid", group);
    }

    private void addSegments(Group group, ArrayList<Vector2> points) {
        startPath(group, points.get(0));
        for (int i = 1; i < points.size(); i++) {
            addPointToPath(group, points.get(i));
        }
        closePath(group);
    }

    private void closePath(Group group) {
        ArrayList<Segment> segments = group.segments;
        Segment lastSegment = segments.get(segments.size() - 1);
        Segment firstSegment = segments.get(0);
        lastSegment.end = firstSegment.start.cpy();
    }

    private void startPath(Group group, Vector2 vector) {
        Segment segment = new Segment();
        segment.start = vector;
        group.segments.add(segment);
    }

    private void addPointToPath(Group group, Vector2 vector) {
        ArrayList<Segment> segments = group.segments;
        Segment lastSegment = segments.get(segments.size() - 1);
        lastSegment.end = vector;
        startPath(group, vector.cpy());
    }
}