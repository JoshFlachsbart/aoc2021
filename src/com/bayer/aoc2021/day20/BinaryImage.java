package com.bayer.aoc2021.day20;

import com.bayer.aoc2021.Utils;
import com.bayer.aoc2021.day03.BinaryNumber;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class BinaryImage {
    boolean[] image;
    int width, height;

    public BinaryImage(int width, int height) {
        this.height = height;
        this.width = width;
        image = new boolean[ width * height ];
    }

    public boolean isInfiniteLight = false;

    public boolean getBit(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return image[x + (y * width)];
        }
        return isInfiniteLight;
    }

    public void setBit(int x, int y, boolean val) {
        image[x + (y * width)] = val;
    }

    public long countLit() {
        var count = 0L;
        for(boolean bit: image) if (bit) count++;
        return count;
    }

    public BinaryNumber readVal(int x, int y) {
        BinaryNumber val = new BinaryNumber();
        for (int yi = -1; yi <= 1; yi++) {
            for (int xi = -1; xi <= 1; xi++) {
                val.appendBit(getBit(x + xi, y + yi));
            }
        }
        return val;
    }

    public BinaryImage sharpen(boolean[] commandSet) {
        final int BUFFER = 1;
        BinaryImage sharpenedImage =
                new BinaryImage(width + (BUFFER * 2), height + (BUFFER * 2));
        for (int y = -BUFFER; y < height+BUFFER; y++) {
            for (int x = -BUFFER; x < width+BUFFER; x++) {
                int command = (int) readVal(x, y).getVal();
                sharpenedImage.setBit(x+BUFFER, y+BUFFER, commandSet[command]);
            }
        }

        // Toggle infinite light if needed.
        sharpenedImage.isInfiniteLight = isInfiniteLight ? commandSet[511] : commandSet[0];

        return sharpenedImage;
    }

    public String printImage() {
        StringBuilder lineBuilder = new StringBuilder();
        int x = 0;
        for (boolean bit: image) {
            lineBuilder.append(bit ? '#' : '.');
            x++;
            if (x % width == 0 && x / width < height) lineBuilder.append("\n");
        }
        System.out.println("width: " + width + " height " + height + " num on: " + countLit() + " infinite light: " + isInfiniteLight);
        System.out.println(lineBuilder);
        return lineBuilder.toString();
    }

    public static boolean[] readCommandLine(String commandLine) {
        boolean[] commands = new boolean[commandLine.length()];
        int x = 0;
        for (char bit: commandLine.toCharArray()) {
            commands[x] = bit == '#';
            x++;
        }
        return commands;
    }

    public static BinaryImage loadImage(List<String> imageLines) {
        BinaryImage image = new BinaryImage(
                imageLines.size(),
                imageLines.get(0).length());
        int y = 0;
        for (String line: imageLines) {
            int x = 0;
            for (char bit: line.toCharArray()) {
                image.setBit(x, y, bit == '#');
                x++;
            }
            y++;
        }
        return image;
    }

    public static long run(int iterations) throws Exception {
        Path p = new Utils().getLocalPath("day20");
        List<String> lines = Files.lines(p).toList();
        boolean[] commandSet = readCommandLine(lines.get(0));
        List<String> imageLines = lines.subList(2, lines.size());
        BinaryImage image = BinaryImage.loadImage(imageLines);
        for (int i = 0; i < iterations; i++) {
            image = image.sharpen(commandSet);
        }
        return image.countLit();
    }
}
