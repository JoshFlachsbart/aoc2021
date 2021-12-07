package com.bayer.aoc2021.day06;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class FishSim2 {
    private final int FISH_NEW_AGE = 8;
    private final int FISH_RESET_AGE = 6;
    ArrayList<Long> fishCount = new ArrayList<>(Arrays.asList(0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L));

    public long step() {
        ArrayList<Long> newFishCount = new ArrayList<>(Arrays.asList(0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L));
        // Age non babies.
        for (int day = 1; day <= FISH_NEW_AGE; day++) {
            newFishCount.set(day - 1, fishCount.get(day));
        }
        // Make babies.
        long babyMakers = fishCount.get(0);
        newFishCount.set(FISH_NEW_AGE, babyMakers);
        // Merge babies that had fish with fish that aged in.
        newFishCount.set(FISH_RESET_AGE, newFishCount.get(FISH_RESET_AGE) + babyMakers);
        fishCount = newFishCount;
        return fishCount();
    }

    public long steps(int numSteps) {
//        IntStream.range(0, numSteps).forEach(this::step);
        for (int step = 0; step < numSteps; step++) {
            step();
            System.out.println("After " + step + " days :" + fishCount());
        }
        return fishCount();
    }

    private long fishCount() {
        long totalNumFish = 0;
        for (long fishDayCount: fishCount) {
            totalNumFish += fishDayCount;
        }
        return totalNumFish;
    }

    private void addFish(int age) {
        if (0 <= age && age < FISH_NEW_AGE)
        fishCount.set(age, fishCount.get(age) + 1);
    }

    public void loadFish(String fishString) {
        Scanner s = new Scanner(fishString);
        s.useDelimiter(",");
        while (s.hasNext()) {
            addFish(s.nextInt());
        }
    }

    private Path getLocalPath(String fileName) throws URISyntaxException, IOException {
        URL file = getClass().getClassLoader().getResource("com/bayer/aoc2021/day06/" + fileName);
        if (file == null) throw new IOException("Unable to locate file: " + fileName);
        return Path.of(file.toURI()).toAbsolutePath();
    }

    public static void main(String[] args) throws Exception{
        FishSim2 main = new FishSim2();
        String fishString = Files.readString(main.getLocalPath("data.txt"));
        main.loadFish(fishString);
        System.out.println("After 80 days: " + main.steps(256));
    }
}
