package com.bayer.aoc2021.day13;

import com.bayer.aoc2021.Utils;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Folder {
    public record Point(int x, int y) {
        public static Point parsePoint(String s) {
            String [] p = s.split(",");
            return new Point(Integer.parseInt(p[0]), Integer.parseInt(p[1]));
        }
    }

    public static Point flipPointY(Point p, int y) {
        return new Point(p.x, -p.y + (y * 2));
    }

    public static Point flipPointX(Point p, int x) {
        return new Point(-p.x + (x * 2), p.y);
    }

    public static List<Point> foldPointsY(List<Point> points, int y) {
        List<Point> foldedPoints = new ArrayList<>();
        for (Point p : points) {
            Point addMe = (p.y > y) ? flipPointY(p, y) : p;
            if (!foldedPoints.contains(addMe)) foldedPoints.add(addMe);
            else System.out.println("Point is contained: " + addMe);
        }
        return foldedPoints;
    }

    public static List<Point> foldPointsX(List<Point> points, int x) {
        List<Point> foldedPoints = new ArrayList<>();
        for (Point p : points) {
            Point addMe = (p.x > x) ? flipPointX(p, x) : p;
            if (!foldedPoints.contains(addMe)) foldedPoints.add(addMe);
            else System.out.println("Point is contained: " + addMe);
        }
        return foldedPoints;
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

    public static void main(String[] args) throws Exception {
        Iterator<String> i = Files.lines(new Utils().getLocalPath("day13")).iterator();
        List<Point> points = loadAllPoints(i);
        System.out.println(Folder.foldPointsX(points, 655).size());
    }
}
