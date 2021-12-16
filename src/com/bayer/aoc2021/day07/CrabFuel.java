package com.bayer.aoc2021.day07;

import com.bayer.aoc2021.Utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.IntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CrabFuel {
    List<Integer> crabPos = new ArrayList<>();

    private static int calcDist(int start, int end) {
        return Math.abs(start - end);
    }

    private static int calcDist2(int start, int end) {
        int n = Math.abs(start - end);
        return (n * (1 + n)) / 2;
    }

    public BestPosition calcTotalDists(int end) {
        return new BestPosition(end,
                crabPos.stream()
                        .map(start -> calcDist(start, end))
                        .reduce(0, Integer::sum));
    }

    public BestPosition calcTotalDists2(int end) {
        return new BestPosition(end,
                crabPos.stream()
                        .map(start -> calcDist2(start, end))
                        .reduce(0, Integer::sum));
    }

    public record BestPosition(int pos, int fuel) {
        public String toString() {
            return String.format("Pos: %d, Fuel: %d", pos, fuel);
        }
    }

    public Optional<BestPosition> findBestPosition(IntFunction<BestPosition> distCalc) {
        int minPos = crabPos.stream().mapToInt(v->v).min().orElseThrow();
        int maxPos = crabPos.stream().mapToInt(v->v).max().orElseThrow();
        return IntStream.rangeClosed(minPos, maxPos)
                .mapToObj(distCalc)
                .min(Comparator.comparingInt(a -> a.fuel));
    }

    public void loadCrabs(String crabString) {
        Scanner s = new Scanner(crabString);
        s.useDelimiter(",");
        while (s.hasNext()) {
            crabPos.add(s.nextInt());
        }
    }

    public static void main(String[] args) throws Exception {
        CrabFuel main = new CrabFuel();
        String crabString = Files.readString(new Utils().getLocalPath("day07"));
        main.loadCrabs(crabString);
        System.out.println(main.findBestPosition(main::calcTotalDists).orElseThrow());
        System.out.println(main.findBestPosition(main::calcTotalDists2).orElseThrow());
    }
}
