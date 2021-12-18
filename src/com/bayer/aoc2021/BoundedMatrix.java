package com.bayer.aoc2021;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BoundedMatrix<T>  {
    private final T[] matrix;
    private final int WIDTH;
    private final int HEIGHT;

    public BoundedMatrix(int width, int height, Class<T> clazz) {
        WIDTH = width;
        HEIGHT = height;
        matrix = (T[]) Array.newInstance(clazz, WIDTH * HEIGHT);
    }

    public int getWidth() { return WIDTH; }

    public int getHeight() { return HEIGHT; }

    public boolean hasPoint(Point p) { return hasPoint( p.x(), p.y() );}

    public boolean hasPoint(int x, int y) { return 0 <= x && x < WIDTH && 0 <= y && y < HEIGHT; }

    public T get (int x, int y) {
        return matrix[x + (WIDTH * y)];
    }

    public Optional<T> boundedGet(Point p) {
        return boundedGet(p.x(),p.y());
    }

    public Optional<T> boundedGet(int x, int y) {
        Optional<T> val = Optional.empty();
        if (hasPoint(x, y)) val = Optional.of(get(x, y));
        return val;
    }

    public void set (int x, int y, T val) {
        matrix[x + (WIDTH * y)] = val;
    }

    public T get (Point p) {
        return get (p.x(), p.y());
    }

    public void set (Point p, T val) {
        set (p.x(), p.y(), val);
    }

    public void setAll (List<Point> points, T val) {
        points.forEach(p -> set(p, val));
    }

    public void setAll (T val) {
        Arrays.fill(matrix, val);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < HEIGHT; y++) {
            T [] line = Arrays.copyOfRange(matrix, WIDTH * y, WIDTH * (y + 1));
            for (T c: line)
                sb.append(c);
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    public static BoundedMatrix<Character> createCharMapFromStrings(List<String> lines) {
        BoundedMatrix<Character> map = new BoundedMatrix<>(lines.get(0).length(), lines.size(), Character.class);
        int y = 0;
        for (String line: lines) {
            if (line.length() != map.WIDTH) throw new IllegalArgumentException("Uneven length lines in input on line " + y);
            int x = 0;
            for (char c: line.toCharArray()) {
                map.set(x,y,c);
                x++;
            }
            y++;
        }
        return map;
    }

    public static void main(String[] args) {
        BoundedMatrix<Character> bm = new BoundedMatrix<>(3,3, Character.class);
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
