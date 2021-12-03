package com.bayer.aoc2021.day02;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PositionCalc {
    private static final String DEFAULT_FILE_NAME = "data.txt";

    public static void main(String[] args) throws Exception {
        PositionCalc main = new PositionCalc();
        String fileName = args.length > 0 ? args[0] : DEFAULT_FILE_NAME;
        List<Command> commandList = main.loadCommandData(fileName);

        Position startPosition = new Position(0,0, 0);

        // Initial version.
        Position position = new Position(startPosition);
        for (Command c : commandList) {
            position = c.runCommand(position);
        }

        Position newPosition = commandListRunner(commandList.listIterator(), startPosition);

//        Position streamPosition = commandList.stream().reduce(
//                startPosition,
//                command -> command.runCommand(p)
//                );


        System.out.println(position);
        System.out.println(newPosition);
        System.out.println(position.depth() * position.distance());
    }

    // TODO turn lisp brain into java brain.
    private static Position commandListRunner (Iterator<Command> commandList, final Position currentPosition) {
        if (!commandList.hasNext())
            return currentPosition;
        else
            return commandListRunner(commandList, commandList.next().runCommand(currentPosition));
    }

    private List<Command> loadCommandData(String fileName) throws IOException, URISyntaxException {
        URL file = getClass().getClassLoader().getResource("com/bayer/aoc2021/day02/" + fileName);
        if (file == null) throw new IOException("Unable to locate file: " + fileName);
        Path absolutePath = Path.of(file.toURI()).toAbsolutePath();
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

    public record Position(int depth, int distance, int aim) {
        Position(Position p) {
            this(p.depth, p.distance, p.aim);
        }

        public Position forward(int value) {
            return new Position(
                    depth + (aim * value),
                    distance + value,
                    aim);
        }

        public Position aim(int value) {
            return new Position(depth, distance, aim + value);
        }
    }


}
