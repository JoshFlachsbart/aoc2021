package com.bayer.aoc2021.day09;


import com.bayer.aoc2021.Utils;


import java.nio.file.Files;
import java.util.*;

public class LocalMinimum {
    int width = -1;
    int height = -1;
    long [][] heightMatrix;
    long [][] basinFill;

    public void loadMatrices(List<String> lines) throws IllegalArgumentException {
        height = lines.size();
        width = lines.get(0).length();
        heightMatrix = new long[height][width];
        basinFill = new long[height][width];
        int x = 0,y = 0;
        for (String line : lines) {
            if (line.length() != width) throw new IllegalArgumentException("Uneven Matrix not allowed.");
            for (char c : line.toCharArray()) {
                heightMatrix[y][x] = c - '0';
                x++;
            }
            y++;
            x = 0;
        }
    }

    public long get(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return heightMatrix[y][x];
        } else {
            return -1;
        }
    }

    public long getBasin(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return basinFill[y][x];
        } else {
            return -1;
        }
    }

    public void setBasin(int x, int y, long basinVal) {
        basinFill[y][x] = basinVal;
    }

    public long findMinima() {
        long danger = 0;
        long basinCount = 1;
        List<Long> basinSizes = new ArrayList<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                long val = get(x,y);
                List<Long> neighbors = new ArrayList<>();
                if (get(x,y-1) >= 0) neighbors.add(get(x, y-1));
                if (get(x,y+1) >= 0) neighbors.add(get(x, y+1));
                if (get(x+1,y) >= 0) neighbors.add(get(x+1, y));
                if (get(x-1,y) >= 0) neighbors.add(get(x-1, y));
                long lowerCount = neighbors.stream().filter(l -> val >= l).count();
                if ( lowerCount == 0 ) {
                    System.out.format("Found minimum %d (%d, %d)\n" , val, x, y);
                    long size = 1 + fillBasin(basinCount, val, x, y-1) +
                            fillBasin(basinCount, val, x, y+1) +
                            fillBasin(basinCount, val, x-1, y) +
                            fillBasin(basinCount, val, x+1, y);
                    basinSizes.add(size);
                    System.out.format("Basin %d with size %d\n", basinCount, size);
                    danger += val + 1;
                    basinCount++;
                }
            }
        }
        basinSizes.sort(Collections.reverseOrder());
        long product = basinSizes.get(0) * basinSizes.get(1) * basinSizes.get(2);
        System.out.println("Product of three largest basins: " + product); // 1038240
        return danger;
    }

    public long fillBasin(long basinCount, long prevVal, int x, int y) {
        long val = get(x,y);
        long basinVal = getBasin(x,y);
        if (val < 0                         // out of array
                || val < prevVal            // not going up
                || val == 9                 // top of the array
                || basinCount == basinVal)  // already counted
            return 0;
        setBasin(x,y,basinCount);
        return 1 +
                fillBasin(basinCount, val, x, y-1) +
                fillBasin(basinCount, val, x, y+1) +
                fillBasin(basinCount, val, x-1, y) +
                fillBasin(basinCount, val, x+1, y);
    }

    public static void main(String[] args) throws Exception {
        LocalMinimum main = new LocalMinimum();
        main.loadMatrices(Files.readAllLines(new Utils().getLocalPath("day09")));
        System.out.println("Score: " + main.findMinima()); // 564
    }
}
