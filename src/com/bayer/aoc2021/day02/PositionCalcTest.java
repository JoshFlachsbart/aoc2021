package com.bayer.aoc2021.day02;

import org.junit.jupiter.api.Assertions;
import java.util.Arrays;
import java.util.List;
import com.bayer.aoc2021.day02.PositionCalc.Position;
import com.bayer.aoc2021.day02.PositionCalc.AimedPosition;
import com.bayer.aoc2021.day02.PositionCalc.Command;
import org.junit.jupiter.api.Test;

class PositionCalcTest {

    @Test
    void runCommandPart1() {
        List<Position> testPositions = Arrays.asList(
                new Position(0, 0),
                new Position(0, 3),
                new Position(4, 3),
                new Position(1, 3),
                new Position(0, 3),
                new Position(0, 2)
        );
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

    @org.junit.jupiter.api.Test
    void runCommand() {
        List<AimedPosition> testPositions  = Arrays.asList(
                new AimedPosition(0,0, 0),
                new AimedPosition(0,5, 0),
                new AimedPosition(0,5, 5),
                new AimedPosition(40,13, 5),
                new AimedPosition(40,13, 2),
                new AimedPosition(44,15, 2)
        );
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

    @Test
    void answer() throws Exception {
        PositionCalc main = new PositionCalc();
        List<Command> commandList = main.loadCommandData();

        Position position = new Position(0, 0);

        for (Command c : commandList) {
            position = c.runCommand(position);
        }

        Assertions.assertEquals(1499229, position.depth() * position.distance());


        PositionCalc.AimedPosition startPosition = new PositionCalc.AimedPosition(0,0,0);
        PositionCalc.AimedPosition aimedPosition = PositionCalc.commandListRunner(commandList.iterator(), startPosition);
        Assertions.assertEquals(1340836560, aimedPosition.depth() * aimedPosition.distance());
    }
}