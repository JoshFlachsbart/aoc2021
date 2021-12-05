package com.bayer.aoc2021.day04;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class BingoGame {
    private static final String DEFAULT_FILE_NAME = "data.txt";

    private Path getLocalPath(String fileName) throws URISyntaxException, IOException {
        URL file = getClass().getClassLoader().getResource("com/bayer/aoc2021/day04/" + fileName);
        if (file == null) throw new IOException("Unable to locate file: " + fileName);
        return Path.of(file.toURI()).toAbsolutePath();
    }

    public static void main(String[] args) throws Exception {
        BingoGame main = new BingoGame();
        Path absolutePath = main.getLocalPath(args.length > 0 ? args[0] : DEFAULT_FILE_NAME);
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
            String line = lineterator.next();
            List<String> boardLines = new ArrayList<>(BingoBoard.BOARD_SIZE);
            for (int i = 0; i < BingoBoard.BOARD_SIZE; i++) {
                boardLines.add(lineterator.next());
            }
            boards.add(new BingoBoard(boardLines));
        }
    }
}
