package com.bayer.aoc2021;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public record Point(long x, long y) {
    public static Point parsePoint(String s) {
        String[] p = s.split(",");
        return new Point(Long.parseLong(p[0]), Long.parseLong(p[1]));
    }

    public static List<Point> loadAllPoints(Iterator<String> pointerator) {
        List<Point> points = new ArrayList<>();
        String line = pointerator.next();
        while (!line.isEmpty()) {
            points.add(Point.parsePoint(line));
            line = pointerator.next();
        }
        return points;
    }
}
