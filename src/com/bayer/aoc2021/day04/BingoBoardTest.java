package com.bayer.aoc2021.day04;

import com.bayer.aoc2021.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

class BingoBoardTest {

    ArrayList<String> testInput = new ArrayList<>(Arrays.asList(
            " 1  2  3  4  5",
            " 6  7  8  9 10",
            "11 12 13 14 15",
            "16 17 18 19 20",
            "21 22 23 24 25"));

    @Test
    void isWinner() {
        BingoBoard b = new BingoBoard(testInput);
        Assertions.assertFalse(b.isWinner(Arrays.asList(1, 4, 3, 7, 9)));
        Assertions.assertTrue(b.isWinner(Arrays.asList(16, 17, 18, 19, 20)));
        Assertions.assertTrue(b.isWinner(Arrays.asList(1, 12, 3, 11, 9, 13, 65, 15, 14)));
        Assertions.assertFalse(b.isWinner(Arrays.asList(1, 4, 3, 7, 9)));
        Assertions.assertTrue(b.isWinner(Arrays.asList(4, 9, 14, 19, 24)));
        Assertions.assertTrue(b.isWinner(Arrays.asList(5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25)));
    }

    @Test
    void calcScore() {
        BingoBoard b = new BingoBoard(testInput);
        Assertions.assertEquals(235, b.calcScore(Arrays.asList(16, 17, 18, 19, 20)));
        Assertions.assertEquals(247, b.calcScore(Arrays.asList(1, 12, 3, 11, 9, 13, 65, 15, 14)));
        Assertions.assertEquals(255, b.calcScore(Arrays.asList(4, 9, 14, 19, 24)));
        Assertions.assertEquals(10, b.calcScore(Arrays.asList(5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25)));
    }

    @Test
    void checkRow() {
        BingoBoard b = new BingoBoard(testInput);
        Assertions.assertFalse(b.checkRow(1, Arrays.asList(1, 4, 3, 7, 9)));
        Assertions.assertTrue(b.checkRow(3, Arrays.asList(16, 17, 18, 19, 20)));
        Assertions.assertTrue(b.checkRow(2, Arrays.asList(1, 12, 3, 11, 9, 13, 65, 15, 14)));
    }

    @Test
    void checkCol() {
        BingoBoard b = new BingoBoard(testInput);
        Assertions.assertFalse(b.checkCol(1, Arrays.asList(1, 4, 3, 7, 9)));
        Assertions.assertTrue(b.checkCol(3, Arrays.asList(4, 9, 14, 19, 24)));
        Assertions.assertTrue(b.checkCol(2, Arrays.asList(18, 13, 3, 11, 9, 8, 65, 23, 14)));
    }


    @Test
    void getRowCol() {
        BingoBoard b = new BingoBoard(testInput);
        Assertions.assertEquals(15, b.get(2, 4));
        Assertions.assertEquals(1, b.get(0, 0));
        Assertions.assertEquals(25, b.get(4, 4));
    }

}