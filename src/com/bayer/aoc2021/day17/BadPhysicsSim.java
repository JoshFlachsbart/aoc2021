package com.bayer.aoc2021.day17;

import com.bayer.aoc2021.Point;
import com.bayer.aoc2021.Utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BadPhysicsSim {
    public record Target(Point topLeft, Point bottomRight) {
        public boolean contains(Point p) {
            return topLeft.x() <= p.x() && p.x() <= bottomRight.x()
                    && topLeft.y() >= p.y() && p.y() >= bottomRight.y();
        }
    }

    public static Optional<Long> sim(long vx, long vy, Target target) {
        long step = 0;
        Point loc = new Point(0,0);
        long max_y = 0;
        while (loc.y() > target.bottomRight.y()) {
            if (step > 0) {
                if (vx > 0) vx -= 1;
                else if (vx < 0) vx += 1;
                // else vx == 0;
                vy -= 1;
            }
            loc = new Point(loc.x() + vx, loc.y() + vy);
            max_y = Math.max(loc.y(), max_y);

            if ( loc.y() < target.bottomRight().y() ) return Optional.empty();
            if ( target.contains(loc) ) return Optional.of(max_y);
            step++;
        }
        return Optional.empty();
    }

    // You want the most number of steps possible.
    // So calc the number of steps at each side then we can iterate for the highest y.
    public static long findMinXVelocity(Target t) {
        long x = t.topLeft().x();
        long vx = x;
        boolean stillReachingTarget = true;
        while (stillReachingTarget) {
            if (calcTotalXDist(vx) > x) vx--;
            else stillReachingTarget = false;
        }
        return vx;
    }

    public static long findMaxXVelocity(Target t) {
        return t.bottomRight.x();
    }

    public static long findYForXStep(long initialYVelocity, long xStep) {
        long y = 0;
        for(long x = 0; x < xStep; x++) {
            y += initialYVelocity;
            initialYVelocity--;
        }
        return  y;
    }

    public static long calcTotalXDist(long initialXVelocity) {
        long totalDist = 0;
        for (long xstep = initialXVelocity; xstep > 0; xstep--) {
            totalDist += xstep;
        }
        // alternate: totalDist = (totalDist * (totalDist + 1)) / 2
        return totalDist;
    }

    public static Target decodeInput(String input) {
        String[] inputs = input.split("(, )| |(\\.\\.)|=");
        long x1 = Long.parseLong(inputs[3]);
        long x2 = Long.parseLong(inputs[4]);
        long y1 = Long.parseLong(inputs[6]);
        long y2 = Long.parseLong(inputs[7]);
        Point topLeft = new Point(Math.min(x1, x2), Math.max(y1, y2));
        Point bottomRight = new Point(Math.max(x1, x2), Math.min(y1, y2));
        System.out.println(topLeft);
        System.out.println(bottomRight);
        return new Target(topLeft, bottomRight);
    }

    public static long maxYPart1 (Target t) {
        long n = -t.bottomRight.y();
        return (n * (n - 1)) / 2;
    }

    public static Target loadTarget() throws Exception {
        Path p = new Utils().getLocalPath("day17");
        return BadPhysicsSim.decodeInput(Files.readString(p));
    }

    public static Map<Point, Long>  run(Target t, long miny, long maxy) {
        long minx = findMinXVelocity(t);
        long maxx = findMaxXVelocity(t);
        System.out.format("Min x: %d, Max x: %d, Min y: %d, Max y: %d\n", minx, maxx, miny, maxy);
        Map<Point, Long> hits = new HashMap<>();
        for (long vx = minx; vx <= maxx; vx++) {
            for (long vy = miny; vy < maxy; vy++) {
                Optional<Long> val = sim(vx, vy, t);
                if (val.isPresent()) {
                    Point v0 = new Point(vx, vy);
                    System.out.println ("Hit at: " + v0 + " and height " + val.get());
                    hits.put(v0, val.get());
                }
            }
        }
        return hits;
    }
}
