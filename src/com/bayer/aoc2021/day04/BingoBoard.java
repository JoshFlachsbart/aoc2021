package com.bayer.aoc2021.day04;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BingoBoard {
    public static final int BOARD_SIZE = 5;

    public Optional<Integer> checkForWinner(List<Integer> numbers) {
        boolean isWinner = false;
        for (int row = 0; row < BOARD_SIZE && !isWinner; row++) isWinner = checkRow(row, numbers);
        for (int col = 0; col < BOARD_SIZE && !isWinner; col++) isWinner = checkCol(col, numbers);

        Optional<Integer> sumOfUnmarked = Optional.empty();
        if (isWinner) {
            int runningScore = 0;
            for( int val: boardArray) {
                if (!numbers.contains(val)) runningScore += val;
            }
            sumOfUnmarked = Optional.of(runningScore);
        }
        return sumOfUnmarked;
    }

    public boolean checkRow(int row, List<Integer> numbers) {
        boolean rowWorking = true;
        for (int col = 0; col < BOARD_SIZE && rowWorking; col++) {
            rowWorking = numbers.contains(get( row, col ));
        }
        return rowWorking;
    }

    public boolean checkCol(int col, List<Integer> numbers) {
        boolean rowWorking = true;
        for (int row = 0; row < BOARD_SIZE && rowWorking; row++) {
            rowWorking = numbers.contains(get( row, col ));
        }
        return rowWorking;
    }

    public record Pos (int row, int col) {
    }

    private static int getIdx( int row, int col ) {
        return row * BOARD_SIZE + col;
    }

    private static int getIdx( Pos p ) {
        return getIdx( p.row, p.col );
    }

    public int get(@NotNull Pos p) {
        return boardArray[getIdx(p)];
    }

    public int get(int row, int col) {
        return boardArray[getIdx(row, col)];
    }

    public Optional<Pos> find(int num) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            int col = board.get(row).indexOf(num);
            if (col >= 0) return Optional.of(new Pos(row, col));
        }
        return Optional.empty();
    }

    private ArrayList<ArrayList<Integer>> board = new ArrayList<>(BOARD_SIZE);
    private int[] boardArray = new int [BOARD_SIZE * BOARD_SIZE];

    public BingoBoard(List<String> boardRows) {
        int idx = 0;
        for(String row: boardRows) {
            ArrayList<Integer> rowArray = new ArrayList<>(Arrays.stream(row.trim().split("\\s+"))
                    .map(i -> Integer.parseInt(i))
                    .collect(Collectors.toList()));
            //board.add(rowArray);
            for (int i: rowArray) boardArray[idx++] = i;
        }
    }
}
