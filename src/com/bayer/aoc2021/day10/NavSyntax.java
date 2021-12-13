package com.bayer.aoc2021.day10;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Collectors;

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

    public static List<Character> autoComplete(String commandLine) {
        Stack<Character> commands = new Stack<>();
        List<Character> completion = new ArrayList<>();
        for (char c: commandLine.toCharArray()) {
            switch (c) {
                case '(', '{', '[', '<' -> commands.push(c);
                case ')', '}', ']', '>' -> commands.pop();
            }
        }
        while (!commands.empty()) {
            switch (commands.pop()) {
                case '(' -> completion.add(')');
                case '{' -> completion.add('}');
                case '[' -> completion.add(']');
                case '<' -> completion.add('>');
            }
        }
        return completion;
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

    public static long getAutocompleteVal(List<Character> completeChars) {
        long score = 0;
        for (char c : completeChars) {
            score *= 5;
            score += switch (c) {
                case ')' -> 1;
                case '}' -> 3;
                case ']' -> 2;
                case '>' -> 4;
                default -> throw new IllegalArgumentException("Unknown error char: " + c);
            };
        }
        return score;
    }

    private Path getLocalPath(String fileName) throws URISyntaxException, IOException {
        URL file = getClass().getClassLoader().getResource("com/bayer/aoc2021/day10/" + fileName);
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

        List<Long> scores = Files.lines(dataPath)
                .filter(line -> NavSyntax.findError(line).isEmpty())
                .map(NavSyntax::autoComplete)
                .map(NavSyntax::getAutocompleteVal)
                .sorted()
                .collect(Collectors.toList());
        System.out.println(scores.get(scores.size() / 2));
    }
}
