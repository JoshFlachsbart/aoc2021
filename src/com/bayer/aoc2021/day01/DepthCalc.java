package com.bayer.aoc2021.day01;

import com.bayer.aoc2021.Utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class DepthCalc {

    public static void main(String[] args) throws IOException, URISyntaxException {
        List<Integer> allDepthValues = DepthCalc.loadDepthData();

        System.out.println("Increases: " + countDepthIncreases(allDepthValues));
        System.out.println("Windowed Increases: " + countDepthWindowIncreases(allDepthValues, 3));
    }

    public static List<Integer> loadDepthData() throws IOException, URISyntaxException {
        Path absolutePath = new Utils().getLocalPath("day01");
        return Files.lines(absolutePath)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    public static int countDepthIncreases(List<Integer> depthValues) {
        int previousDepth = Integer.MAX_VALUE;
        int numberOfIncreases = 0;
        for (int currentDepth : depthValues) {
            if (currentDepth > previousDepth) numberOfIncreases++;
            previousDepth = currentDepth;
        }
        return numberOfIncreases;
    }

    public static int countDepthWindowIncreases(List<Integer> depthValues, int windowSize) {
        int previousWindowDepth = Integer.MAX_VALUE;
        int numberOfIncreases = 0;
        int maxOffset = depthValues.size() - windowSize;
        for (int offset = 0; offset <= maxOffset; offset++) {
            int currentWindowDepth = calcDepthWindow(depthValues, windowSize, offset);
            if (currentWindowDepth > previousWindowDepth) numberOfIncreases++;
            previousWindowDepth = currentWindowDepth;
        }
        return numberOfIncreases;
    }

    public static int calcDepthWindow(List<Integer> depthValues, int windowSize, int offset) {
        int depthWindowSum = 0;
        for (int i = 0; i < windowSize; i++) {
            depthWindowSum += depthValues.get(offset + i);
        }
        return depthWindowSum;
    }
}
