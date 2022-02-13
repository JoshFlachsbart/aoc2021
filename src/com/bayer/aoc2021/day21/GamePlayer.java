package com.bayer.aoc2021.day21;

import java.util.List;

public class GamePlayer {
    int name;
    int score;
    int position = 0; // board values are 0 - 9 but inputs are 1-10

    static int dice = 0; // dice values are 0-99 but outputs are 1-100
    static int rollCount = 0;

    public void takeTurn() {
        position = (position + roll() + roll() + roll()) % BOARD_SIZE;
        score += position + 1;
    }

    public int roll() {
        int val = getDice();
        rollCount++;
        dice = val % DICE_SIZE;
        return val;
    }

    public int getDice() {
        return dice + 1;
    }

    public void setPosition(int pos) {
        position = pos - 1;
    }

    public boolean isWinner() {
        return score >= WINNING_SCORE;
    }

    public String toString() {
        return String.format("Player %d [pos: %d, score: %d]", name, position+1, score);
    }

    public GamePlayer(int name, int position) {
        this.name = name;
        setPosition(position);
    }



    static final int BOARD_SIZE = 10;
    static final int DICE_SIZE = 100;
    static final int WINNING_SCORE = 1000;

    static List<GamePlayer> players;
    static int currentPlayerIndex = 0;

    public static void setupGame(List<GamePlayer> players) {
        GamePlayer.players = players;
        dice = 0;
        rollCount = 0;
        currentPlayerIndex = 0;
    }

    public static boolean takeTurns() {
        GamePlayer currentPlayer = players.get(currentPlayerIndex);
        currentPlayer.takeTurn();
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        return currentPlayer.isWinner();
    }

    public static int calcScore() {
        GamePlayer loser = players.stream().filter(p -> !p.isWinner()).findFirst().orElseThrow();
        return loser.score * rollCount;
    }
}
