package com.bayer.aoc2021.day02;

import com.bayer.aoc2021.Utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class PositionCalc {
    public static void main(String[] args) throws Exception {
        PositionCalc main = new PositionCalc();
        List<Command> commandList = main.loadCommandData();

        Position position = new Position(0,0);
        for (Command c : commandList) {
            position = c.runCommand(position);
        }

        AimedPosition startPosition = new AimedPosition(0,0,0);
        AimedPosition newAimedPosition = commandListRunner(commandList.iterator(), startPosition);
        System.out.println(position);
        System.out.println(position.depth() * position.distance());
        System.out.println(newAimedPosition);
        System.out.println(newAimedPosition.depth() * newAimedPosition.distance());
    }

    // TODO turn lisp brain into java brain.
    public static AimedPosition commandListRunner (Iterator<Command> commandList, final AimedPosition currentPosition) {
        if (!commandList.hasNext())
            return currentPosition;
        else
            return commandListRunner(commandList, commandList.next().runCommand(currentPosition));
    }

    public List<Command> loadCommandData() throws IOException, URISyntaxException {
        Path absolutePath = new Utils().getLocalPath("day02");
        return Files.lines(absolutePath)
                .map(Command::parseCommand)
                .collect(Collectors.toList());
    }

    public enum CommandType {
        UP("up"),
        DOWN("down"),
        FORWARD("forward");
        String typeName;

        CommandType(String typeName) {
            this.typeName = typeName;
        }

        private static final Map<String, CommandType> NAME_MAP = new HashMap<>();

        public static CommandType lookup(String commandName) {
            return NAME_MAP.get(commandName);
        }

        static {
            for (CommandType e: values()) {
                NAME_MAP.put(e.typeName, e);
            }
        }
    }

    public record Command(CommandType command, int value) {
        public Position runCommand(Position currentPosition) {
            return switch (command) {
                case FORWARD-> new Position(currentPosition.depth(), currentPosition.distance() + value);
                case DOWN -> new Position(currentPosition.depth() + value, currentPosition.distance());
                case UP -> new Position(currentPosition.depth() > value ? currentPosition.depth() - value : 0, currentPosition.distance());
            };
        }

        public AimedPosition runCommand(AimedPosition currentPosition) {
            return switch (command) {
                case FORWARD-> currentPosition.forward(value);
                case DOWN -> currentPosition.aim(value);
                case UP -> currentPosition.aim(-value);
            };
        }

        public static Command parseCommand(String commandLine) {
            Scanner commandParser = new Scanner(commandLine);
            CommandType commandType = CommandType.lookup(commandParser.next());
            if (commandType == null) throw new IllegalArgumentException("malformed command");
            int commandValue = commandParser.nextInt();
            return new Command(commandType, commandValue);
        }
    }

    public record Position(int depth, int distance) {

    }

    public record AimedPosition(int depth, int distance, int aim) {
        public AimedPosition forward(int value) {
            return new AimedPosition(
                    depth + (aim * value),
                    distance + value,
                    aim);
        }

        public AimedPosition aim(int value) {
            return new AimedPosition(depth, distance, aim + value);
        }

    }
}
