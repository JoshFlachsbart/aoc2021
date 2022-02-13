package com.bayer.aoc2021.day21;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.bayer.aoc2021.day21.QuantumGamePlayer.ScoreMap;
import com.bayer.aoc2021.day21.QuantumGamePlayer.RecursionState;
import com.bayer.aoc2021.day21.QuantumGamePlayer.Wins;

import static com.bayer.aoc2021.day21.QuantumGamePlayer.recurse;

class QuantumGamePlayerTest {

    @Test
    void scoreMapUpdates() {
        QuantumGamePlayer q = new QuantumGamePlayer();
        ScoreMap map = q.startingScoreMap();

        //map.print();
        ScoreMap map2 = map.updateScores(1,0, 1);
        //map2.print();
        Assertions.assertEquals(1, map2.scoreCount[1][0]);
        Assertions.assertEquals(1, map2.runningGameCount());
        Assertions.assertEquals(0, q.bWinCount);
        map2 = map2.updateScores(0,3, 1);
        //map2.print();
        Assertions.assertEquals(1, map2.scoreCount[1][3]);
        Assertions.assertEquals(1, map2.runningGameCount());
        Assertions.assertEquals(0, q.bWinCount);
        for (int i = 0; i < 6; i++) map2 = map2.updateScores(0,3, 1);
        //map2.print();
        Assertions.assertEquals(0, map2.runningGameCount());
        Assertions.assertEquals(1, q.bWinCount);
    }

    @Test
    void scoreMapAdd() {
        QuantumGamePlayer q = new QuantumGamePlayer();
        ScoreMap map = q.startingScoreMap();
        ScoreMap map2 = map.updateScores(1,0, 1);
        map2 = map.addScores(map2);
        Assertions.assertEquals(1, map2.scoreCount[0][0]);
        Assertions.assertEquals(1, map2.scoreCount[1][0]);
        Assertions.assertEquals(2, map2.runningGameCount());
        Assertions.assertEquals(0, q.aWinCount);
    }

    @Test
    void quantumMadness() {
        QuantumGamePlayer.resetForTest();
        Wins wins = recurse(new RecursionState(QuantumGamePlayer.Turns.A_TURN, 4, 8, 0, 0));
        Assertions.assertEquals(new Wins(444356092776315L, 341960390180808L), wins);
    }

    @Test
    void waysToWinFromState() {
        QuantumGamePlayer.resetForTest();
        RecursionState fromHere = new RecursionState(QuantumGamePlayer.Turns.A_TURN, 1,1,20,20);
        Assertions.assertEquals(new Wins(27,0), recurse(fromHere));
        fromHere = new RecursionState(QuantumGamePlayer.Turns.B_TURN, 1,1,20,20);
        Assertions.assertEquals(new Wins(0,27), recurse(fromHere));
    }

    @Test
    void answer() {
        QuantumGamePlayer.resetForTest();
        Wins wins = recurse(new RecursionState(QuantumGamePlayer.Turns.A_TURN, 3, 10, 0, 0));
        Assertions.assertEquals(new Wins(92399285032143L, 85783200461468L), wins);
    }

}