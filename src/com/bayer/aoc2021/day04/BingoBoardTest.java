package com.bayer.aoc2021.day04;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import com.bayer.aoc2021.day04.BingoBoard.Pos;

import static org.junit.jupiter.api.Assertions.*;

class BingoBoardTest {

    ArrayList<String> testInput = new ArrayList<>(Arrays.asList(
            " 1  2  3  4  5",
            " 6  7  8  9 10",
            "11 12 13 14 15",
            "16 17 18 19 20",
            "21 22 23 24 25"));

    @Test
    void checkBoard() {
        BingoBoard b = new BingoBoard(testInput);
        Assertions.assertEquals(false, b.checkForWinner(Arrays.asList(1, 4, 3, 7, 9)).isPresent());
        Assertions.assertEquals(235, b.checkForWinner(Arrays.asList(16, 17, 18, 19, 20)).get());
        Assertions.assertEquals(247, b.checkForWinner(Arrays.asList(1, 12, 3, 11, 9, 13, 65, 15, 14)).get());
        Assertions.assertEquals(false, b.checkForWinner(Arrays.asList(1, 4, 3, 7, 9)).isPresent());
        Assertions.assertEquals(255, b.checkForWinner(Arrays.asList(4, 9, 14, 19, 24)).get());
        Assertions.assertEquals(10, b.checkForWinner(Arrays.asList(5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25)).get());
    }

    @Test
    void checkRow() {
        BingoBoard b = new BingoBoard(testInput);
        Assertions.assertEquals(false, b.checkRow(1, Arrays.asList(1, 4, 3, 7, 9)));
        Assertions.assertEquals(true, b.checkRow(3, Arrays.asList(16, 17, 18, 19, 20)));
        Assertions.assertEquals(true, b.checkRow(2, Arrays.asList(1, 12, 3, 11, 9, 13, 65, 15, 14)));
    }

    @Test
    void checkCol() {
        BingoBoard b = new BingoBoard(testInput);
        Assertions.assertEquals(false, b.checkCol(1, Arrays.asList(1, 4, 3, 7, 9)));
        Assertions.assertEquals(true, b.checkCol(3, Arrays.asList(4, 9, 14, 19, 24)));
        Assertions.assertEquals(true, b.checkCol(2, Arrays.asList(18, 13, 3, 11, 9, 8, 65, 23, 14)));
    }

    @Test
    void getPos() {
        BingoBoard b = new BingoBoard(testInput);
        Assertions.assertEquals(15, b.get(new Pos(2, 4)));
        Assertions.assertEquals(1, b.get(new Pos(0, 0)));
        Assertions.assertEquals(25, b.get(new Pos(4, 4)));
    }

    @Test
    void getRowCol() {
        BingoBoard b = new BingoBoard(testInput);
        Assertions.assertEquals(15, b.get(2, 4));
        Assertions.assertEquals(1, b.get(0, 0));
        Assertions.assertEquals(25, b.get(4, 4));
    }

    @Test
    void find() {
        BingoBoard b = new BingoBoard(testInput);
        Assertions.assertEquals(new Pos(2, 4), b.find(15).get());
        Assertions.assertEquals(new Pos(0, 0), b.find(1).get());
        Assertions.assertEquals(new Pos(4, 4), b.find(25).get());
        Assertions.assertEquals(false, b.find(35).isPresent());
    }
}