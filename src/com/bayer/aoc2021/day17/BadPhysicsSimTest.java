package com.bayer.aoc2021.day17;

import com.bayer.aoc2021.Point;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

class BadPhysicsSimTest {
    @Test
    void distCalc() {
        Assertions.assertEquals(6, BadPhysicsSim.calcTotalXDist(3));
        Assertions.assertEquals(15, BadPhysicsSim.calcTotalXDist(5));
        Assertions.assertEquals(171, BadPhysicsSim.calcTotalXDist(18));
    }

    @Test
    void sim()  {
        BadPhysicsSim.Target targetA = new BadPhysicsSim.Target(new Point(20, -5), new Point(30, -10));
        Assertions.assertEquals(3, BadPhysicsSim.sim(7,2, targetA).orElseThrow());
        Assertions.assertEquals(0, BadPhysicsSim.sim(9,0, targetA).orElseThrow());
        Assertions.assertEquals(6, BadPhysicsSim.sim(6,3, targetA).orElseThrow());
        Assertions.assertFalse(BadPhysicsSim.sim(17,-4, targetA).isPresent());
        Assertions.assertEquals(45, BadPhysicsSim.run(targetA, 0, 30).values().stream().max(Long::compare).orElse(-1L));
    }

    @Test
    void answer() throws Exception {
        BadPhysicsSim.Target t = BadPhysicsSim.loadTarget();
        System.out.println(BadPhysicsSim.maxYPart1(t));
        Map<Point, Long> hits = BadPhysicsSim.run(t, -1000, 1000);
        Assertions.assertEquals(5253, hits.values().stream().max(Long::compare).orElse(-1L));
        Assertions.assertEquals(1770, hits.size());
    }
}