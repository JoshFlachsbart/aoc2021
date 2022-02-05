package com.bayer.aoc2021.day03;

import com.bayer.aoc2021.Utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class PowerCalc {
    public static void main(String[] args) throws Exception {
        Path absolutePath = new Utils().getLocalPath("day03");

        final PerBitCounter perBitCounter = new PerBitCounter();
        long count = Files.lines(absolutePath)
                .map(BinaryNumber::new)
                .peek(perBitCounter::updateArray)
                .count();
        long gamma = perBitCounter.getGamma((int) count).getVal();
        long epsilon = perBitCounter.getEpsilon((int) count).getVal();

        System.out.println("Count: " + count);
        System.out.println("Gamma: " + gamma);
        System.out.println("Epsilon: " + epsilon);
        System.out.println("Power: " + gamma * epsilon); // 4160394

        List <BinaryNumber> values = Files.lines(absolutePath)
                .map(BinaryNumber::new)
                .collect(Collectors.toList());
        long O2 = BitFrequencyFilter.filterO2(values).getVal();
        long C02 = BitFrequencyFilter.filterCO2(values).getVal();
        System.out.println("O2 " +  O2);
        System.out.println("CO2 " +  C02);
        System.out.println("Life Support: " + O2 * C02); // 4125600
    }

    public static class BitFrequencyFilter {
        public static boolean areMoreBitsSetForOffset(List<BinaryNumber> values, int offset) {
            long oneCount = values.stream().filter(b -> b.getBit(offset)).count();
            long zeroCount = values.size() - oneCount;
            return oneCount >= zeroCount;
        }

        public static List<BinaryNumber> filter(List<BinaryNumber> values, int offset, boolean isO2) {
            if (values.size() == 1) return values;
            boolean mostSet = areMoreBitsSetForOffset(values, offset);
            // For O2 we want the most frequent bit, of CO2 we want the least frequent.
            boolean filterBit = isO2 ? mostSet : !mostSet;
            return filter(
                    values.stream()
                            .filter(b -> b.getBit(offset) == filterBit)
                            .collect(Collectors.toList()),
                    offset+1,
                    isO2);

        }

        public static BinaryNumber filterO2(List<BinaryNumber> values) {
            return filter(values, 0, true).get(0);
        }

        public static BinaryNumber filterCO2(List<BinaryNumber> values) {
            return filter(values, 0, false).get(0);
        }
    }

    public static class PerBitCounter {
        private final List<Integer> bitCounts = Arrays.stream(new int[12]).boxed().collect(Collectors.toList());

        private void updateArray(BinaryNumber b) {
            int offset = 0;
            for (boolean bit : b) {
                if (bit) bitCounts.set(offset, bitCounts.get(offset) + 1);
                offset++;
            }
        }

        private BinaryNumber getGamma(int count) {
            BinaryNumber b = new BinaryNumber();
            for (int i : bitCounts) b.appendBit(i > count - i);
            return b;
        }

        private BinaryNumber getEpsilon(int count) {
            BinaryNumber b = new BinaryNumber();
            for (int i : bitCounts) b.appendBit(i < count - i);
            return b;
        }
    }
}
