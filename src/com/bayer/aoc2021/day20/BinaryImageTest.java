package com.bayer.aoc2021.day20;

import com.bayer.aoc2021.Utils;
import com.bayer.aoc2021.day03.BinaryNumber;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BinaryImageTest {
    String image = """
                #..#.
                #....
                ##..#
                ..#..
                ..###""";

    @Test
    void countLit() {
        String round1 = """
                .##.##.
                #..#.#.
                ##.#..#
                ####..#
                .#..##.
                ..##..#
                ...#.#.""";
        String round2 = """
                .......#.
                .#..#.#..
                #.#...###
                #...##.#.
                #.....#.#
                .#.#####.
                ..#.#####
                ...##.##.
                ....###..""";
        BinaryImage bi = BinaryImage.loadImage(image.lines().toList());
        Assertions.assertEquals(10, bi.countLit());
        Assertions.assertEquals(5, bi.width);
        boolean [] commands = BinaryImage.readCommandLine("..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..###..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###.######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#..#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#......#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#.....####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#.......##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#");
        BinaryImage sharpen1 = bi.sharpen(commands);
        Assertions.assertEquals(round1, sharpen1.printImage());
        BinaryImage sharpen2 = sharpen1.sharpen(commands);
        Assertions.assertEquals(round2, sharpen2.printImage());
        Assertions.assertEquals(35, sharpen2.countLit());
    }

    @Test
    void binaryDecode() {
        BinaryImage bi = BinaryImage.loadImage(image.lines().toList());
        BinaryNumber bn = bi.readVal(2,2);
        Assertions.assertEquals(34, bn.getVal());
    }

    @Test
    void answer() throws Exception {
        Path p = new Utils().getLocalPath("day20");
        List<String> lines = Files.lines(p).toList();
        boolean[] commandSet = BinaryImage.readCommandLine(lines.get(0));
        List<String> imageLines = lines.subList(2, lines.size());
        BinaryImage image = BinaryImage.loadImage(imageLines);
        image.printImage();
        BinaryImage image1 =  image.sharpen(commandSet);
        image1.isInfiniteLight = true;
        image1.printImage();
        BinaryImage image2 =  image1.sharpen(commandSet);
        image2.printImage();
        // Too high.
        Assertions.assertEquals(5347, BinaryImage.run());
    }
}