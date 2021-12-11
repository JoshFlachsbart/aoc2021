package com.bayer.aoc2021.day10;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

public class NavSyntax {
    public static Optional<Character> findError(String commandLine) {
        Stack<Character> commands = new Stack<>();
        for (char c: commandLine.toCharArray()) {
            switch (c) {
                case '(', '{', '[', '<':
                    commands.push(c);
                    break;
                case ')':
                    if (commands.peek() == '(') commands.pop();
                    else return Optional.of(c);
                    break;
                case '}':
                    if (commands.peek() == '{') commands.pop();
                    else return Optional.of(c);
                    break;
                case ']':
                    if (commands.peek() == '[') commands.pop();
                    else return Optional.of(c);
                    break;
                case '>':
                    if (commands.peek() == '<') commands.pop();
                    else return Optional.of(c);
                    break;
            }
        }
        return Optional.empty();
    }

    public static long getErrorVal(char errorChar) {
        return switch (errorChar) {
            case ')' -> 3;
            case '}' -> 1197;
            case ']' -> 57;
            case '>' -> 25137;
            default -> throw new IllegalArgumentException("Unknown error char: " + errorChar);
        };
    }

    private Path getLocalPath(String fileName) throws URISyntaxException, IOException {
        URL file = getClass().getClassLoader().getResource("com/bayer/aoc2021/day08/" + fileName);
        if (file == null) throw new IOException("Unable to locate file: " + fileName);
        return Path.of(file.toURI()).toAbsolutePath();
    }

    public static void main(String[] args) throws Exception {
        NavSyntax main = new NavSyntax();
        Path dataPath = main.getLocalPath("data.txt");

        Long errorSum = Files.lines(dataPath)
                .map(line -> NavSyntax.findError(line))
                .flatMap(Optional::stream)
                .map(NavSyntax::getErrorVal)
                .reduce(Long::sum).orElseThrow();

        System.out.println(errorSum);
    }
}
