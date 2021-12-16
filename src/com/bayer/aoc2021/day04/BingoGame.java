package com.bayer.aoc2021.day04;

import com.bayer.aoc2021.Utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class BingoGame {
    public static void main(String[] args) throws Exception {
        BingoGame main = new BingoGame();
        Path absolutePath = new Utils().getLocalPath("day04");
        List<String> lines = Files.readAllLines(absolutePath);
        Iterator<String> lineterator = lines.iterator();
        List<Integer> numbers = Arrays.stream(lineterator.next().split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        main.loadAllGames(lineterator);

        for (int draw: numbers) {
            main.boards.removeAll(main.checkForWinner(draw));
        }
        System.out.println("Boards left " + main.boards.size());

    }

    List<BingoBoard> boards = new LinkedList<>();
    List<Integer> numbers = new LinkedList<>();

    public List<BingoBoard> checkForWinner(int draw) {
        numbers.add(draw);
        List<BingoBoard> winners = new LinkedList<>();
        for (BingoBoard board: boards) {
            if (board.isWinner(numbers)) {
                winners.add(board);
                System.out.println("Winner found at " + draw +
                        " winning score: " + board.calcScore(numbers) * draw);
            }
        }
        return winners;
    }

    public void loadAllGames(Iterator<String> lineterator) {
        while(lineterator.hasNext()) {
            lineterator.next(); // eat line
            List<String> boardLines = new ArrayList<>(BingoBoard.BOARD_SIZE);
            for (int i = 0; i < BingoBoard.BOARD_SIZE; i++) {
                boardLines.add(lineterator.next());
            }
            boards.add(new BingoBoard(boardLines));
        }
    }
}
