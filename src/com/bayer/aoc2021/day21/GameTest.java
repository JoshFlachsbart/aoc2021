package com.bayer.aoc2021.day21;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class GameTest {
    private static void checkPlayer ( GamePlayer p, int position, int dice, int score ) {
        Assertions.assertEquals(position, p.position, "Wrong position " + p);
        Assertions.assertEquals(dice, GamePlayer.dice, "Wrong dice " + p);
        Assertions.assertEquals(score, p.score, "Wrong score " + p);
    }

    @Test
    void takeTurn() {
        GamePlayer.dice = 0;
        GamePlayer p1 = new GamePlayer(1, 4);
        GamePlayer p2 = new GamePlayer(2, 1);
        p1.takeTurn();
        checkPlayer(p1, 9,3,10);
        p2.takeTurn();
        checkPlayer(p2, 5,6,6);
    }

    @Test
    void fullTurns() {
        GamePlayer p1 = new GamePlayer(1, 4);
        GamePlayer p2 = new GamePlayer(2, 8);
        GamePlayer.setupGame(List.of(p1,p2));
        GamePlayer.takeTurns();
        checkPlayer(p1, 9,3,10);
        GamePlayer.takeTurns();
        checkPlayer(p2, 2,6,3);
        GamePlayer.takeTurns();
        GamePlayer.takeTurns();
        checkPlayer(p1, 3,12,14);
        checkPlayer(p2, 5,12,9);
        boolean done = false;
        while (!done) done = GamePlayer.takeTurns();
        checkPlayer(p1, 9,93,1000);
        checkPlayer(p2, 2,93,745);
        Assertions.assertEquals(993, GamePlayer.rollCount);
        Assertions.assertEquals(739785, GamePlayer.calcScore());
    }

    @Test
    void roll() {
        GamePlayer.dice = 0;
        GamePlayer p = new GamePlayer(1, 4);
        Assertions.assertEquals(1, p.roll());
        Assertions.assertEquals(2, p.roll());
        GamePlayer p2 = new GamePlayer(2, 1);
        Assertions.assertEquals(3, p2.roll());
    }

    @Test
    void answer() {
        GamePlayer p1 = new GamePlayer(1, 3);
        GamePlayer p2 = new GamePlayer(2, 10);
        GamePlayer.setupGame(List.of(p1,p2));
        boolean done = false;
        while (!done) done = GamePlayer.takeTurns();
        System.out.println(GamePlayer.calcScore());

    }
}