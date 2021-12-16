package com.bayer.aoc2021.day08;


import com.bayer.aoc2021.Utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DigitDecoder {
    private final ArrayList<String> correctDigits = new ArrayList<>(List.of(
            "abcefg",  // 0 -> 6
            "cf",      // 1 easy -> 2
            "acdeg",   // 2 -> 5
            "acdfg",   // 3 -> 5 (has 1)
            "bcdf",    // 4 easy -> 4
            "abdfg",   // 5 -> 5
            "abdefg",  // 6 -> 6 (no 7)
            "acf",     // 7 easy -> 3
            "abcdefg", // 8 easy -> 7
            "abcdfg"   // 9 -> 6 (has 4)
    ));

    private ArrayList<String> sortedDigits = new ArrayList<>(List.of(
            "", "", "", "", "", "", "", "", "", ""
    ));

    List<Integer> digitsToCount = List.of( 2, 4, 3, 7 );

    public long letterCounter(Collection<String> sevenSegmentDigits) {
        return sevenSegmentDigits.stream()
                .mapToInt(String::length)
                .filter(digitsToCount::contains)
                .count();
    }

    private String zero;  // 6 (has 1, no 4)
    private String one;   // 2 *
    private String two;   // 5 (6 doesn't have 2)
    private String three; // 5 (has 7)
    private String four;  // 4 *
    private String five;  // 5 (six has 5, but not 2)
    private String six;   // 6 (no 1, no 4)
    private String seven; // 3 *
    private String eight; // 7 *
    private String nine;  // 6 (has 4)


    public static boolean hasNumber( String doesItHave, String thisOne ) {
        boolean contains = true;
        for (char c: thisOne.toCharArray()) {
            if (!doesItHave.contains("" + c)) {
                contains = false;
                break;
            }
        }
        return contains;
    }

    public void found(int number, String digit) {
        String sortedDigit = digit.chars().sorted()
                .mapToObj(c -> String.valueOf((char) c)).collect(Collectors.joining());
        sortedDigits.set(number, sortedDigit);
    }

    public String get(int number) {
        return sortedDigits.get(number);
    }

    public int get(String digit) {
        String sortedDigit = digit.chars().sorted()
                .mapToObj(c -> String.valueOf((char) c)).collect(Collectors.joining());
        return sortedDigits.indexOf(sortedDigit);
    }

    public void loadDigits(String[] digits) {
        // find easy
        for (String digit: digits) {
            switch (digit.length()) {
                case 2 -> found(1, digit);
                case 4 -> found(4, digit);
                case 3 -> found(7, digit);
                case 7 -> found(8, digit);
            }
        }
        // find 6
        for (String digit: digits) {
            if (digit.length() == 6) {
                if (!hasNumber(digit, get(4))) {
                    if (hasNumber(digit, get(1))) {
                        found(0, digit);
                    } else {
                        found(6, digit);
                    }
                } else {
                    found(9, digit);
                }
            }
        }
        // find 5
        for (String digit: digits) {
            if (digit.length() == 5) {
                if (hasNumber(digit, get(7))) {
                    found(3, digit);
                } else if (hasNumber(get(6), digit)) {
                    found(5, digit);
                } else {
                    found(2, digit);
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        DigitDecoder main = new DigitDecoder();
        Path dataPath = new Utils().getLocalPath("day08");

        List<String> digits = Files.lines(dataPath)
                .map(line -> line.split("\\|")[1].trim())
                .map(line -> line.split(" "))
                .flatMap(Stream::of)
                .collect(Collectors.toList());

        System.out.println("All easy digits in answer: " + main.letterCounter(digits));

        List<String> allLines = Files.readAllLines(dataPath);
        long sum = 0;
        for (String line: allLines) {
            String[] lineParts = line.split("\\|");
            main.loadDigits(lineParts[0].trim().split(" "));
            long val = 0;
            for (String digit: lineParts[1].trim().split(" ")) {
                val *= 10;
                val += main.get(digit);
            }
            System.out.println(val);
            sum += val;
        }
        System.out.println(sum);
    }
}
