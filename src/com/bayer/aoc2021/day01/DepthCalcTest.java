package com.bayer.aoc2021.day01;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class DepthCalcTest {

    @org.junit.jupiter.api.Test
    void countDepthIncreases() {
        Assertions.assertEquals(DepthCalc.countDepthIncreases(Arrays.asList(12, 14, 16)), 2);
        Assertions.assertEquals(DepthCalc.countDepthIncreases(Arrays.asList(12, 11, 10)), 0);
        Assertions.assertEquals(DepthCalc.countDepthIncreases(Arrays.asList(12, 10, 11)), 1);
    }

    @org.junit.jupiter.api.Test
    void calcWindowDepth() {
        Assertions.assertEquals(DepthCalc.calcDepthWindow(Arrays.asList(12, 14, 16), 2, 0), 26);
        Assertions.assertEquals(DepthCalc.calcDepthWindow(Arrays.asList(12, 11, 10), 2, 1), 21);
        Assertions.assertEquals(DepthCalc.calcDepthWindow(Arrays.asList(12, 10, 11), 3, 0), 33);
    }

    @org.junit.jupiter.api.Test
    void countWindowDepthIncreases() {
        Assertions.assertEquals(DepthCalc.countDepthWindowIncreases(Arrays.asList(12, 14, 16), 2), 1);
        Assertions.assertEquals(DepthCalc.countDepthWindowIncreases(Arrays.asList(12, 11, 10), 2), 0);
        Assertions.assertEquals(DepthCalc.countDepthWindowIncreases(Arrays.asList(12, 10, 11, 12, 13), 2), 2);
    }

    @Test
    void answer() throws Exception {
        List<Integer> allDepthValues = DepthCalc.loadDepthData();

        Assertions.assertEquals(1709, DepthCalc.countDepthIncreases(allDepthValues));
        Assertions.assertEquals(1761, DepthCalc.countDepthWindowIncreases(allDepthValues, 3));

    }
}