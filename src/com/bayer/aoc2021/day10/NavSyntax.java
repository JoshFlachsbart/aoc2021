package com.bayer.aoc2021.day10;

import com.bayer.aoc2021.Utils;

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

    public static void main(String[] args) throws Exception {
        Path dataPath = new Utils().getLocalPath("day10");

        Long errorSum = Files.lines(dataPath)
                .map(NavSyntax::findError)
                .flatMap(Optional::stream)
                .map(NavSyntax::getErrorVal)
                .reduce(Long::sum).orElseThrow();

        System.out.println(errorSum); // 339411

        List<Long> scores = Files.lines(dataPath)
                .filter(line -> NavSyntax.findError(line).isEmpty())
                .map(NavSyntax::autoComplete)
                .map(NavSyntax::getAutocompleteVal)
                .sorted()
                .collect(Collectors.toList());
        System.out.println(scores.get(scores.size() / 2)); // 2289754624
    }
}
