package com.bayer.aoc2021.day15;

import com.bayer.aoc2021.Point;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class PathRiskTest {
    @Test
    void pathRisk() {
        String map2 = """
                11
                13""";
        PathRisk pr2 = new PathRisk();
        pr2.fill(map2.lines().toList());
        Assertions.assertEquals(4, pr2.bestRisk().getScore());
        pr2.buildPathScores();

        String map = """
                1163751742
                1381373672
                2136511328
                3694931569
                7463417111
                1319128137
                1359912421
                3125421639
                1293138521
                2311944581""";
        PathRisk pr = new PathRisk();
        pr.fill(map.lines().toList());
        Assertions.assertEquals(40, pr.bestRisk().getScore());
        Assertions.assertEquals(40, pr.buildPathScores());

        PathRisk pr3 = new PathRisk();
        pr3.überFill(pr);
//        Assertions.assertEquals(315, pr3.bestRisk().getScore());
        Assertions.assertEquals(315, pr3.buildPathScores());
    }
}
