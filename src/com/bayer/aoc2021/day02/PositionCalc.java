package com.bayer.aoc2021.day02;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PositionCalc {

    public static void main(String[] args) throws Exception {
        PositionCalc main = new PositionCalc();
        List<Command> sampleList = main.loadCommandData("data.txt");

        Position position = new Position(0,0);

        // TODO make this not sideeffecting.
        for (Command c : sampleList) {
            position = c.runCommand(position);
        }

        System.out.println(position);
        System.out.println(position.depth() * position.distance());
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

        public static CommandType commandTypeByName(String commandName) {
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

        public static Command parseCommand(String commandLine) {
            String[] parts = commandLine.split(" ");
//            if (parts.length != 2) throw new Exception("malformed command exception");
            return new Command(CommandType.commandTypeByName(parts[0]), Integer.parseInt(parts[1]));
        }
    }

    public record Position(int depth, int distance) {

    }


}
