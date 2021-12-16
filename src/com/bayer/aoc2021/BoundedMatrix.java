package com.bayer.aoc2021;

import java.util.Arrays;
import java.util.List;

public class BoundedMatrix  {
    private final char[] matrix;
    private final int WIDTH;
    private final int HEIGHT;

    public BoundedMatrix(int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        matrix = new char[WIDTH*HEIGHT];
    }

    public char get (int x, int y) {
        return matrix[x + (WIDTH * y)];
    }

    public void set (int x, int y, char val) {
        matrix[x + (WIDTH * y)] = val;
    }

    public char get (Point p) {
        return get (p.x(), p.y());
    }

    public void set (Point p, char val) {
        set (p.x(), p.y(), val);
    }

    public void setAll (List<Point> points, char val) {
        points.forEach(p -> set(p, val));
    }

    public void setAll (char val) {
        Arrays.fill(matrix, val);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < HEIGHT; y++) {
            sb.append(Arrays.copyOfRange(matrix, WIDTH * y, WIDTH * (y + 1)));
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        BoundedMatrix bm = new BoundedMatrix(3,3);
        bm.set(0,0,'1');
        bm.set(1,0,'2');
        bm.set(2,0,'3');
        bm.set(0,1,'4');
        bm.set(1,1,'5');
        bm.set(2,1,'6');
        bm.set(0,2,'7');
        bm.set(1,2,'8');
        bm.set(2,2,'9');
        System.out.println(bm);
    }
}
