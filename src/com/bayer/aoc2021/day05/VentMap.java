package com.bayer.aoc2021.day05;

import com.bayer.aoc2021.Utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.IntStream;

public class VentMap {
    private static final int X = 0;
    private static final int Y = 1;
    private final int size;
    private final int[] map;
    private final boolean diags;

    public VentMap(int size, boolean diags) {
        this.size = size;
        this.diags = diags;
        map = new int[size*size];
    }

    public void set(int x, int y) {
        map[x * size + y]++;
    }

    public int get(int x, int y) {
        return map[x * size + y];
    }

    public int countMin( int minVal ) {
        int count = 0;
        for (int i: map) {
            if (i >= minVal) count++;
        }
        return count;
    }

    public void loadLine(int[] p1, int[] p2) {
        if (p1[X] == p2[X]) {
            int startY = Math.min(p1[Y],p2[Y]);
            int endY = Math.max(p1[Y],p2[Y]);
            IntStream.rangeClosed(startY, endY).forEach(y -> set(p1[X], y));
        } else if (p1[Y] == p2[Y]) {
            int startX = Math.min(p1[X],p2[X]);
            int endX = Math.max(p1[X],p2[X]);
            IntStream.rangeClosed(startX, endX).forEach(x -> set(x, p1[Y]));
        } else if (diags && Math.abs(p2[X] - p1[X]) == Math.abs(p2[Y] - p1[Y])) {
            int xStep = p1[X] < p2[X] ? 1 : -1;
            int yStep = p1[Y] < p2[Y] ? 1 : -1;
            for (int x = p1[X], y = p1[Y]; !(p2[X] == x && p2[Y] == y); x += xStep, y += yStep) {
                set(x,y);
            }
            set(p2[X], p2[Y]);
        } else {
            System.out.format("Skipping line: (%d,%d) -> (%d,%d)\n", p1[X], p1[Y], p2[X], p2[Y]);
        }
    }

    private int[] parsePoint ( String point ) {
        String[] pointParts = point.split(",");
        return new int[] {
                Integer.parseInt(pointParts[X]),
                Integer.parseInt(pointParts[Y])
        };
    }

    public void parseAndLoadLine( String line ) {
        String [] points = line.split(" -> ");
        loadLine(
                parsePoint(points[0]),
                parsePoint(points[1]));
    }

    public static void main(String[] args) throws Exception {
        Path path = new Utils().getLocalPath("day05");
        VentMap main = new VentMap(1000, false);
        Files.lines(path).forEach(main::parseAndLoadLine);
        System.out.println(main.countMin(2));
        main = new VentMap(1000, true);
        Files.lines(path).forEach(main::parseAndLoadLine);
        System.out.println(main.countMin(2));
    }
}
