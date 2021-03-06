package com.bayer.aoc2021.day13;

import com.bayer.aoc2021.BoundedMatrix;
import com.bayer.aoc2021.Point;
import com.bayer.aoc2021.Utils;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class Folder {
    public static List<Point> foldPointsY(List<Point> points, long y) {
        List<Point> foldedPoints = new ArrayList<>();
        for (Point p : points) {
            Point addMe = (p.y() > y) ? new Point(p.x(),  (y*2) - p.y()) : p;
            if (!foldedPoints.contains(addMe)) foldedPoints.add(addMe);
            //else System.out.println("Point is contained: " + addMe);
        }
        return foldedPoints;
    }

    public static List<Point> foldPointsX(List<Point> points, long x) {
        List<Point> foldedPoints = new ArrayList<>();
        for (Point p : points) {
            Point addMe = (p.x() > x) ? new Point((x*2) - p.x(), p.y()) : p;
            if (!foldedPoints.contains(addMe)) foldedPoints.add(addMe);
            // else System.out.println("Point is contained: " + addMe);
        }
        return foldedPoints;
    }

    public static void main(String[] args) throws Exception {
        Iterator<String> i = Files.lines(new Utils().getLocalPath("day13")).iterator();
        List<Point> points = Point.loadAllPoints(i);
        boolean first = true;
        while (i.hasNext()) {
            String [] instruction = i.next().split(" ")[2].split("=");
            points = switch (instruction[0]) {
                case "x" -> Folder.foldPointsX(points, Integer.parseInt(instruction[1]));
                case "y" -> Folder.foldPointsY(points, Integer.parseInt(instruction[1]));
                default -> throw new IllegalArgumentException("Unknown instruction: " + instruction[0]);
            };
            if (first) {
                first = false;
                System.out.println(points.size()); // 592
            }
        }
        int maxX = (int) points.stream().max(Comparator.comparingLong(Point::x)).orElseThrow().x();
        int maxY = (int) points.stream().max(Comparator.comparingLong(Point::y)).orElseThrow().y();
        BoundedMatrix<Character> folded = new BoundedMatrix<>(maxX+1, maxY+1, Character.class);
        folded.setAll('.');
        folded.setAll(points, '#');
        System.out.println(folded); // JGAJEFKU
    }
}
