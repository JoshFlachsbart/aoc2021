package com.bayer.aoc2021.day06;

import com.bayer.aoc2021.Utils;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

public class FishSim {
    private final int FISH_NEW_AGE = 8;
    private final int FISH_RESET_AGE = 6;
    ArrayList<Integer> fish = new ArrayList<>(1000);

    public int step() {
        int startingFish = fish.size();
        for (int offset = 0; offset < startingFish; offset++) {
            int fishAge = fish.get(offset);
            if (fishAge == 0) {
                fish.set(offset, FISH_RESET_AGE);
                fish.add(FISH_NEW_AGE);
            } else {
                fish.set(offset, fishAge-1);
            }
        }
        return fish.size();
    }

    public int steps(int numSteps) {
//        IntStream.range(0, numSteps).forEach(this::step);
        for (int step = 0; step < numSteps; step++) {
            step();
            System.out.println("After " + step + " days :" + fish.size());
        }
        return fish.size();
    }

    public void printFish() {
        String comma = "";
        for (int fishVal: fish) {
            System.out.print(comma + fishVal);
            comma = ",";
        }
        System.out.println();
    }

    public void loadFish(String fishString) {
        Scanner s = new Scanner(fishString);
        s.useDelimiter(",");
        while (s.hasNext()) {
            fish.add(s.nextInt());
        }
    }

    public static void main(String[] args) throws Exception{
        FishSim main = new FishSim();
        String fishString = Files.readString(new Utils().getLocalPath("day06"));
        main.loadFish(fishString);
        System.out.println("After 80 days: " + main.steps(80)); // 393019
    }
}
