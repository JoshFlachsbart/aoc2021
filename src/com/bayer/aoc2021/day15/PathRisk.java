package com.bayer.aoc2021.day15;

import com.bayer.aoc2021.BoundedMatrix;
import com.bayer.aoc2021.Point;
import com.bayer.aoc2021.Utils;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PathRisk {
    private BoundedMatrix<Character> map;
    private Point end;

    public class Path {
        private final List<Point> path;
        private final int score;

        public String toString() {
            return "score: " + score + " " + path;
        }

        public Path (List<Point> path, int score) {
            this.path = path;
            this.score = score;
        }

        public boolean betterThan(Path p) {
            return score < p.score;
        }

        public boolean contains(Point p) {
            return path.contains(p);
        }

        public Point getLast() {
            return path.get(path.size()-1);
        }

        public int getScore() {
            return score;
        }

        public Optional<Path> extendPath(Point p) {
            if (contains(p) || !map.hasPoint(p)) return Optional.empty();
            else {
                List<Point> newPath = new ArrayList<>(path);
                newPath.add(p);
                return Optional.of(new Path(newPath, score + (map.get(p) - '0')));
            }
        }

    }

    public Path bestRiskHelper(Path bestPath, Path currentPath, Point addMe) {
        Optional<Path> newPathOpt = currentPath.extendPath(addMe);
        if (newPathOpt.isEmpty()) {
            return bestPath; // we left the board or doubled back.
        }
        Path newPath = newPathOpt.get();
        if (bestPath.betterThan(newPath)) {
            return bestPath; // another path already beat us
        } else if (addMe.equals(end)) {
//            System.out.println(newPath);
            return newPath; // we are at the end and better than any previous path.
        } else {
            Path newBest = bestRiskHelper(bestPath, newPath, new Point(addMe.x() + 1, addMe.y()));
            newBest = bestRiskHelper(newBest, newPath, new Point(addMe.x(), addMe.y() + 1));
//            newBest = bestRiskHelper(newBest, newPath, new Point(addMe.x() - 1, addMe.y()));
//            newBest = bestRiskHelper(newBest, newPath, new Point(addMe.x(), addMe.y() - 1));
            return newBest;
        }
    }


    public Path bestRisk() {
        Path worstPath = new Path(new ArrayList<>(), Integer.MAX_VALUE);
        Point start = new Point(0,0);
        Path starterPath = new Path(List.of(start), 0);
        Path newBest = bestRiskHelper(worstPath, starterPath, new Point(start.x() + 1, start.y()));
        newBest = bestRiskHelper(newBest, starterPath, new Point(start.x(), start.y() + 1));
        return newBest;

    }

    public int buildPathScores() {
        BoundedMatrix<Integer> scores = new BoundedMatrix<>(map.getWidth(), map.getHeight(), Integer.class);
        scores.set(0,0,0);
        for (int i = 1; i < map.getWidth(); i++) {
            for (int x = i, y = 0; x >= 0 && y <= i; x--, y++) {
                scores.set(x, y, calcPathScore(scores, x, y));
            }
        }
        for (int i = 1; i < map.getWidth(); i++) {
            for (int x = i, y = map.getHeight() - 1; x < map.getWidth() && y >= i; x++, y--) {
                scores.set(x, y, calcPathScore(scores, x, y));
            }
        }
        System.out.println(map);
        return scores.get(end);
    }

    private int calcNextDist(Point p, BoundedMatrix<Integer> scores) {
        int nextDist = Integer.MAX_VALUE;
        if (scores.hasPoint(p) && scores.get(p) == Integer.MAX_VALUE) {
            nextDist = map.boundedGet(p).orElseThrow();
        }
        return nextDist;
    }

    private List<Point> getNeighbors(Point p) {
        return List.of(new Point(p.x()-1,p.y()),
                new Point(p.x(),p.y()-1),
                new Point(p.x()+1,p.y()),
                new Point(p.x(),p.y()+1));
    }

    public int buildPathScoresFULLDIJKSTRA() {
        BoundedMatrix<Integer> scores = new BoundedMatrix<>(map.getWidth(), map.getHeight(), Integer.class);
        scores.setAll(Integer.MAX_VALUE);
        Point start = new Point(0,0);
        List<Point> visitedLocations = new ArrayList<>();
        visitedLocations.add(start);
        scores.set(start,0);
        // for each visited location, find the next shortest distance from the start.
        int i = 1000;
        while (scores.get(end) == Integer.MAX_VALUE) {
            if (visitedLocations.size() % 1000 == 0) {System.out.println(i);i+=1000;}
            List<Point> completed = new ArrayList<>();
            Point nextClosest = null;
            int nextClosestDist = Integer.MAX_VALUE;
            for (Point p : visitedLocations) {
                int currentDist = scores.get(p);
                boolean isDone = true;
                for (Point next : getNeighbors(p)) {
                    if (scores.hasPoint(next) && scores.get(next) == Integer.MAX_VALUE) {
                        isDone = false;
                        int nextVal = Character.digit(map.boundedGet(next).orElseThrow(), 10) + currentDist;
                        if (nextVal < nextClosestDist) {
                            nextClosestDist = nextVal;
                            nextClosest = next;
                        }
                    }
                }
                if (isDone) completed.add(p);
            }
            visitedLocations.add(nextClosest);
            if (nextClosest == null) throw new IllegalArgumentException();
            scores.set(nextClosest, nextClosestDist);
            visitedLocations.removeAll(completed);
        }
        return scores.get(end);
    }

    private int calcPathScore(BoundedMatrix<Integer> scores, int x, int y) {
        int val1 = scores.boundedGet(x-1, y).orElse(Integer.MAX_VALUE);
        int val2 = scores.boundedGet(x, y-1).orElse(Integer.MAX_VALUE);
        int curr = map.get(x, y) - '0';
        int bestSoFar = Math.min(val1, val2);
        return bestSoFar + curr;
    }

    public void fill(List<String> lines) {
        map = BoundedMatrix.createCharMapFromStrings(lines);
        end = new Point(map.getWidth() - 1, map.getHeight() - 1);
    }

    public void überFill(PathRisk initialPath) {
        final int WIDTH = initialPath.map.getWidth();
        final int HEIGHT = initialPath.map.getHeight();
        map = new BoundedMatrix<>(WIDTH * 5,HEIGHT * 5, Character.class);
        for (int X = 0; X < 5; X++) {
            for (int Y = 0; Y < 5; Y++) {
                final int XOFFSET = WIDTH * X;
                final int YOFFSET = HEIGHT * Y;
                final int INCREMENT = X + Y;
                for (int x = 0; x < WIDTH; x++) {
                    for (int y = 0; y < HEIGHT; y++) {
                        try {
                            int val = Character.digit(initialPath.map.get(x, y), 10);
                            int newVal = val + INCREMENT;
                            newVal = newVal > 9 ? newVal - 9 : newVal;
                            map.set(x + XOFFSET, y + YOFFSET, Character.forDigit(newVal, 10));
                        } catch (Exception e) { System.out.format("%d %d\n", x, y); }
                    }
                }
            }
        }
        end = new Point(map.getWidth() - 1, map.getHeight() - 1);
    }

    public static void main(String[] args) throws Exception {
        PathRisk pr = new PathRisk();
        pr.fill(Files.readAllLines(new Utils().getLocalPath("day15")));
        System.out.println(pr.buildPathScores());

        PathRisk pr2 = new PathRisk();
        pr2.überFill(pr);
        System.out.println(pr2.buildPathScoresFULLDIJKSTRA());
    }
}
