package com.bayer.aoc2021.day11;

import com.bayer.aoc2021.Utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class EnergyMap {
    private int[][] energyMap;
    private int width;
    private int height;

    public String printMap() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                char c = Character.forDigit(energyMap[x][y], 10);
                sb.append(c);
            }
            if (y != height - 1) sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    public void loadMap(List<String>rows) {
        height = rows.size();
        width = rows.get(1).length();
        energyMap = new int[width][height];
        int y = 0;
        int x = 0;
        for (String line: rows) {
            for (char c: line.toCharArray()) {
                energyMap[x][y] = Character.digit(c, 10);
                x++;
            }
            x = 0;
            y++;
        }
    }

    public void incrementElement(int x, int y) {
        if (0 <= x && x < width && 0 <= y && y < height) {
            energyMap[x][y]++;
            if (energyMap[x][y] == 10) {
                incrementElement(x-1,y-1);
                incrementElement(x-1,y);
                incrementElement(x-1,y+1);
                incrementElement(x,y-1);
                incrementElement(x,y+1);
                incrementElement(x+1,y-1);
                incrementElement(x+1,y);
                incrementElement(x+1,y+1);
            }
        }
    }

    public long update() {
        long flashCount = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                incrementElement(x, y);
            }
        }
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (energyMap[x][y] >= 10) {
                    energyMap[x][y] = 0;
                    flashCount++;
                }
            }
        }
        return flashCount;
    }


    public long simCount(int steps) {
        long flashCount = 0;
        for (int i = 0; i < steps; i++) {
            flashCount += update();
        }
        return flashCount;
    }

    public long simFindAll() {
        long stepCount = 0;
        long allFlashed = (long) width*height;
        boolean found = false;
        while (!found) {
            stepCount++;
            found = allFlashed == update();
            if (stepCount % 100 == 0) System.out.format("Step: %d\n", stepCount);
        }
        return stepCount;
    }

    public static void main(String[] args) throws Exception {
        Path dataPath = new Utils().getLocalPath("day11");
        EnergyMap map = new EnergyMap();
        map.loadMap(Files.lines(dataPath).toList());
        System.out.println(map.simCount(100)); // 1717

        map.loadMap(Files.lines(dataPath).toList());
        System.out.println(map.simFindAll()); // 476
    }
}
