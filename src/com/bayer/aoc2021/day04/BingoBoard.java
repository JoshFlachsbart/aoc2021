package com.bayer.aoc2021.day04;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BingoBoard {
    public static final int BOARD_SIZE = 5;

    public boolean isWinner(List<Integer> numbers) {
        boolean isWinner = false;
        for (int row = 0; row < BOARD_SIZE && !isWinner; row++) isWinner = checkRow(row, numbers);
        for (int col = 0; col < BOARD_SIZE && !isWinner; col++) isWinner = checkCol(col, numbers);
        return isWinner;
    }

    public int calcScore(List<Integer> numbers) {
        int runningScore = 0;
        for( int val: boardArray) {
            if (!numbers.contains(val)) runningScore += val;
        }
        return runningScore;
    }

    public boolean checkRow(int row, List<Integer> numbers) {
        boolean possibleWinner = true;
        for (int col = 0; col < BOARD_SIZE && possibleWinner; col++) {
            possibleWinner = numbers.contains(get( row, col ));
        }
        return possibleWinner;
    }

    public boolean checkCol(int col, List<Integer> numbers) {
        boolean possibleWinner = true;
        for (int row = 0; row < BOARD_SIZE && possibleWinner; row++) {
            possibleWinner = numbers.contains(get( row, col ));
        }
        return possibleWinner;
    }

    public int get(int row, int col) {
        return boardArray[row * BOARD_SIZE + col];
    }

    private int[] boardArray = new int [BOARD_SIZE * BOARD_SIZE];

    public BingoBoard(List<String> boardRows) {
        int idx = 0;
        for(String row: boardRows) {
            ArrayList<Integer> rowArray = Arrays.stream(row.trim().split("\\s+"))
                    .map(Integer::parseInt)
                    .collect(Collectors.toCollection(ArrayList::new));
            //board.add(rowArray);
            for (int i: rowArray) boardArray[idx++] = i;
        }
    }
}
