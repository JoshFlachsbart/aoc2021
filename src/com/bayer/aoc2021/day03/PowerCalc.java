package com.bayer.aoc2021.day03;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class PowerCalc {
    private static final String DEFAULT_FILE_NAME = "data.txt";

    private List<Integer> bitCounts = Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0);

    private void updateArray(String line) {
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '1') bitCounts.set(i, bitCounts.get(i).intValue()+1);
        }
    }

    private int getGamma(int count) {
        StringBuilder b = new StringBuilder();
        for (int i : bitCounts) {
            if (i > count - i) b.append('1');
            else b.append('0');
        }
        System.out.println(b);
        return Integer.parseInt(b.toString(), 2);
    }

    private int getEpsilon(int count) {
        StringBuilder b = new StringBuilder();
        for (int i : bitCounts) {
            if (i < count - i) b.append('1');
            else b.append('0');
        }
        System.out.println(b);
        return Integer.parseInt(b.toString(), 2);
    }

    private Path getLocalPath(String fileName) throws URISyntaxException, IOException {
        URL file = getClass().getClassLoader().getResource("com/bayer/aoc2021/day03/" + fileName);
        if (file == null) throw new IOException("Unable to locate file: " + fileName);
        return Path.of(file.toURI()).toAbsolutePath();
    }

    public static void main(String[] args) throws Exception {
        PowerCalc main = new PowerCalc();
        Path absolutePath = main.getLocalPath(args.length > 0 ? args[0] : DEFAULT_FILE_NAME);
        long count = Files.lines(absolutePath)
                .peek(main::updateArray)
                .count();

        main.bitCounts.stream().forEach(i -> System.out.println(i));
        System.out.println("Count: " + count);
        int gamma = main.getGamma((int) count);
        int epsilon = main.getEpsilon((int) count);
        System.out.println("Gamma: " + gamma);
        System.out.println("Epsilon: " + epsilon);
        System.out.println("Power: " + gamma * epsilon);

        List <String> values = Files.lines(absolutePath).collect(Collectors.toList());
        int O2 = Integer.parseInt(BitFrequencyFilter.filterO2(values), 2);
        int C02 = Integer.parseInt(BitFrequencyFilter.filterO2(values), 2);
        System.out.println("O2 " +  O2);
        System.out.println("CO2 " +  C02);
        System.out.println("Lifr Support: " + O2 * C02);
    }

    public class BitFrequencyFilter {
        public static char getFilterBit(List<String> values, int offset, boolean isO2) {
            long oneCount = values.stream().filter(s -> s.charAt(offset) == '1').count();
            long zeroCount = values.size() - oneCount;
            char filterBit;
            if (isO2) filterBit = oneCount >= zeroCount ? '1' : '0';
            else filterBit = zeroCount <= oneCount ? '0' : '1';
            return filterBit;
        }

        public static List<String> filter(List<String> values, int offset, boolean isO2) {
            if (values.size() == 1) return values;
            char filterBit = getFilterBit(values, offset, isO2);
            return filter(
                    values.stream()
                            .filter(string -> string.charAt(offset) == filterBit)
                            .collect(Collectors.toList()),
                    offset+1,
                    isO2);

        }

        public static String filterO2(List<String> values) {
            return filter(values, 0, true).get(0);
        }

        public static String filterCO2(List<String> values) {
            return filter(values, 0, false).get(0);
        }
    }

}
