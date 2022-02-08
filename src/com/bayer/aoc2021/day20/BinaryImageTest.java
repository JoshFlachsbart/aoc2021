package com.bayer.aoc2021.day20;


import com.bayer.aoc2021.day03.BinaryNumber;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BinaryImageTest {
    String commandString = "..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..###..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###.######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#..#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#......#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#.....####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#.......##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#";
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
        boolean [] commands = BinaryImage.readCommandLine(commandString);
        BinaryImage sharpen1 = bi.sharpen(commands);
        Assertions.assertEquals(round1, sharpen1.printImage());
        BinaryImage sharpen2 = sharpen1.sharpen(commands);
        Assertions.assertEquals(round2, sharpen2.printImage());
        Assertions.assertEquals(35, sharpen2.countLit());
    }

    @Test
    void countLit50() {
        BinaryImage bi = BinaryImage.loadImage(image.lines().toList());
        boolean [] commands = BinaryImage.readCommandLine(commandString);
        for (int i = 0; i < 50; i++) {
            bi = bi.sharpen(commands);
        }
        Assertions.assertEquals(3351, bi.countLit());
    }

    @Test
    void binaryDecode() {
        BinaryImage bi = BinaryImage.loadImage(image.lines().toList());
        BinaryNumber bn = bi.readVal(2,2);
        Assertions.assertEquals(34, bn.getVal());
    }

    @Test
    void answer() throws Exception {
        Assertions.assertEquals(5347, BinaryImage.run(2));
        System.out.println(BinaryImage.run(50));
    }
}