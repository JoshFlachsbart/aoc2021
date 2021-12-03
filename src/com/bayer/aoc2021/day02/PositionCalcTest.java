package com.bayer.aoc2021.day02;

import org.junit.jupiter.api.Assertions;
import java.util.Arrays;
import java.util.List;
import com.bayer.aoc2021.day02.PositionCalc.Position;
import com.bayer.aoc2021.day02.PositionCalc.Command;

class PositionCalcTest {
    private List<Position> testPositions  = Arrays.asList(
        new Position(0,0, 0),
        new Position(0,5, 0),
        new Position(0,5, 5),
        new Position(40,13, 5),
        new Position(40,13, 2),
        new Position(44,15, 2)
    );

    @org.junit.jupiter.api.Test
    void runCommand() {
        try {
            Assertions.assertEquals(testPositions.get(1), Command.parseCommand( "forward 5" ).runCommand(testPositions.get(0)));
            Assertions.assertEquals(testPositions.get(2), Command.parseCommand(    "down 5" ).runCommand(testPositions.get(1)));
            Assertions.assertEquals(testPositions.get(3), Command.parseCommand( "forward 8" ).runCommand(testPositions.get(2)));
            Assertions.assertEquals(testPositions.get(4), Command.parseCommand(      "up 3" ).runCommand(testPositions.get(3)));
            Assertions.assertEquals(testPositions.get(5), Command.parseCommand( "forward 2" ).runCommand(testPositions.get(4)));
        } catch (Exception e) {
            Assertions.fail();
        }
    }
}