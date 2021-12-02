package com.bayer.aoc2021.day02;

import org.junit.jupiter.api.Assertions;
import java.util.Arrays;
import java.util.List;
import com.bayer.aoc2021.day02.PositionCalc.Position;
import com.bayer.aoc2021.day02.PositionCalc.Command;

class PositionCalcTest {
/*
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
*/
    private List<Position> testPositions  = Arrays.asList(
        new Position(0,0),
        new Position(0,3),
        new Position(4,3),
        new Position(1,3),
        new Position(0,3),
        new Position(0,2)
    );

    @org.junit.jupiter.api.Test
    void runCommand() {
        try {
            Assertions.assertEquals(testPositions.get(1), Command.parseCommand("forward 3").runCommand(testPositions.get(0)));
            Assertions.assertEquals(testPositions.get(2), Command.parseCommand("down 4").runCommand(testPositions.get(1)));
            Assertions.assertEquals(testPositions.get(3), Command.parseCommand("up 3").runCommand(testPositions.get(2)));
            Assertions.assertEquals(testPositions.get(4), Command.parseCommand("up 2").runCommand(testPositions.get(3)));
            Assertions.assertEquals(testPositions.get(5), Command.parseCommand("forward -1").runCommand(testPositions.get(4)));
        } catch (Exception e) {
            Assertions.fail();
        }
    }
}