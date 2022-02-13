package com.bayer.aoc2021.day21;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class QuantumGamePlayer {
    // Left so posterity can see my pain and suffering.
    // <editor-fold desc="SECTION OF PAIN">
    long aWinCount = 0;
    long bWinCount = 0;

    public ScoreMap startingScoreMap() {
        ScoreMap sm = new ScoreMap();
        sm.scoreCount[0][0] = 1;
        return sm;
    }

    public static final int WIN_SCORE = 21;
    private final static int MAX_POS = 10;
    // 3d3 probability map
    public static final int[] UNIVERSE_COUNTS =
            { 0, 0, 0, 1 ,3 ,6, 7, 6, 3, 1};
            //0  1  2  3  4  5  6  7  8

    public static final int[] UNIVERSES =
            { 3, 4, 4, 4, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 7, 7, 7, 7, 7, 7, 8, 8, 8, 9 };

    public class PositionMap {
        ScoreMap[][] positionCount = new ScoreMap[MAX_POS][MAX_POS];

        public PositionMap(boolean isStart)
        {
            for (int aScore = 0; aScore < MAX_POS; aScore++) {
                for (int bScore = 0; bScore < MAX_POS; bScore++) {
                    positionCount[aScore][bScore] = new ScoreMap();
                }
            }
            if (isStart) {
                positionCount[4][8] = startingScoreMap();
            }
        }

        public long runningGameCount() {
            long count = 0;
            for (int aScore = 0; aScore < MAX_POS; aScore++) {
                for (int bScore = 0; bScore < MAX_POS; bScore++) {
                    count += positionCount[aScore][bScore].runningGameCount();
                }
            }
            return count;
        }

        public String toString() {
            long running = runningGameCount();
            return String.format("Game scores running: %d, A wins: %d, B wins: %d", running, aWinCount, bWinCount);
        }

        public void print() {
            System.out.println(this);
            for (int aScore = 0; aScore < MAX_POS; aScore++) {
                for (int bScore = 0; bScore < MAX_POS; bScore++) {
                    System.out.print(" " + positionCount[aScore][bScore].runningGameCount());
                }
                System.out.print('\n');
            }
        }

        private void pileOnA(PositionMap p, int aPos, int bPos) {
            for (int i = 3; i <= 9; i++) {
                int newAPos = (aPos + i) % MAX_POS;
                p.positionCount[newAPos][bPos] =
                        p.positionCount[newAPos][bPos]
                                .addScores(positionCount[aPos][bPos].updateScores(newAPos, 0, UNIVERSE_COUNTS[i]));

            }
        }

        public PositionMap playerATurn() {
            PositionMap p = new PositionMap(false);

            for (int aPos = 0; aPos < MAX_POS; aPos++) {
                for (int bPos = 0; bPos < MAX_POS; bPos++) {
                    pileOnA(p, aPos, bPos);
                }
            }
            return p;
        }

        private void pileOnB(PositionMap p, int aPos, int bPos) {
            for (int i = 3; i <= 9; i++) {
                int newBPos = (bPos + i) % MAX_POS;
                p.positionCount[aPos][newBPos] =
                        p.positionCount[aPos][newBPos]
                                .addScores(positionCount[aPos][bPos].updateScores(0, newBPos, UNIVERSE_COUNTS[i]));
            }
        }

        public PositionMap playerBTurn() {
            PositionMap p = new PositionMap(false);

            for (int aPos = 0; aPos < MAX_POS; aPos++) {
                for (int bPos = 0; bPos < MAX_POS; bPos++) {
                    pileOnB(p, aPos, bPos);
                }
            }
            return p;
        }
    }

    public class ScoreMap {
        long[][] scoreCount = new long[WIN_SCORE][WIN_SCORE];

        public ScoreMap()
        {
            for (int aScore = 0; aScore < WIN_SCORE; aScore++) {
                for (int bScore = 0; bScore < WIN_SCORE; bScore++) {
                    scoreCount[aScore][bScore] = 0;
                }
            }
        }

        public ScoreMap addScores(ScoreMap mapB) {
            ScoreMap newMap = new ScoreMap();
            for (int aScore = 0; aScore < WIN_SCORE; aScore++) {
                for (int bScore = 0; bScore < WIN_SCORE; bScore++) {
                    newMap.scoreCount[aScore][bScore] = scoreCount[aScore][bScore] + mapB.scoreCount[aScore][bScore];
                }
            }
            return newMap;
        }

        public ScoreMap updateScores(int scoreA, int scoreB, long numScores) {
            ScoreMap m = new ScoreMap();
            for (int aScore = 0; aScore < WIN_SCORE; aScore++) {
                for (int bScore = 0; bScore < WIN_SCORE; bScore++) {
                    if (aScore + scoreA >= WIN_SCORE) aWinCount += scoreCount[aScore][bScore] * numScores;
                    else if (bScore + scoreB >= WIN_SCORE) bWinCount += scoreCount[aScore][bScore] * numScores;
                    else m.scoreCount[aScore + scoreA][bScore + scoreB] = scoreCount[aScore][bScore] * numScores;
                }
            }
            return m;
        }

        public long runningGameCount() {
            long count = 0;
            for (int aScore = 0; aScore < WIN_SCORE; aScore++) {
                for (int bScore = 0; bScore < WIN_SCORE; bScore++) {
                    count += scoreCount[aScore][bScore];
                }
            }
            return count;
        }


        public String toString() {
            return String.format("Game scores running: %d", runningGameCount());
        }

        public void print() {
            System.out.println(this);
            for (int aScore = 0; aScore < WIN_SCORE; aScore++) {
                for (int bScore = 0; bScore < WIN_SCORE; bScore++) {
                    System.out.print(" " + scoreCount[aScore][bScore]);
                }
                System.out.print('\n');
            }
        }

    }

    public void takeTurnA() {
        currentPositions = currentPositions.playerATurn();
    }

    public void takeTurnB() {
        currentPositions = currentPositions.playerBTurn();
    }

    PositionMap currentPositions = new PositionMap(true);

    public static void runPainAndSuffering(String[] args) {
        QuantumGamePlayer q = new QuantumGamePlayer();

        Scanner s = new Scanner(System.in);
        q.currentPositions.print();
        boolean isA = true;
        while (!"q".equals(s.next())) {
            if (isA) q.takeTurnA();
            else q.takeTurnB();
            isA = !isA;
            q.currentPositions.print();
        }

    }
    // </editor-fold>>

    public static void main(String... args) {
        RecursionState initialState = new RecursionState(
                QuantumGamePlayer.Turns.A_TURN,
                Integer.parseInt(args[0]),
                Integer.parseInt(args[1]),
                0, 0);
        System.out.println(recurse(initialState));
    }

    public static record Wins(long winsA, long winsB) {
        public Wins accumulateWins(Wins toSum, int numTimes) {
            return new Wins(
                    (toSum.winsA() * numTimes) + winsA(),
                    (toSum.winsB() * numTimes) + winsB());
        }
    }

    public enum Turns {
        A_TURN,
        B_TURN
    }

    public static record RecursionState(Turns whosTurn, int aPos, int bPos, long scoreA, long scoreB) {

    }

    public static Map<RecursionState, Wins> winsFromState = new HashMap<>(4000);
    public static void resetForTest() {
        winsFromState = new HashMap<>(4000);
    }

    public static Wins recurse(RecursionState state) {
        // What's this called?  Monadization?? Memoization?? Something like that??
        // TODO figure out if there is some way to cache calls in Java.
        if (winsFromState.containsKey(state)) return winsFromState.get(state);
        if (state.scoreA() >= 21) return new Wins(1, 0);
        if (state.scoreB() >= 21) return new Wins(0, 1);

        //System.out.println(state);
        Wins wins =  switch (state.whosTurn()) {
            case A_TURN -> playerAsTurnRecursionVersion(state);
            case B_TURN -> playerBsTurnRecursionVersion(state);
        };
        winsFromState.put(state, wins);
        return wins;
    }

    public static Wins playerAsTurnRecursionVersion(RecursionState state) {
        Wins wins = new Wins(0, 0);
        for (int i = 3; i <= 9; i++) {
            int newAPos = (state.aPos() + i - 1) % MAX_POS + 1;
            long newAScore = state.scoreA() + newAPos;

            wins = wins.accumulateWins(
                    recurse(new RecursionState(Turns.B_TURN, newAPos, state.bPos(), newAScore, state.scoreB())),
                    UNIVERSE_COUNTS[i]);
        }
        winsFromState.put(state, wins);
        return wins;
    }

    public static Wins playerBsTurnRecursionVersion(RecursionState state) {
        Wins wins = new Wins(0, 0);
        // for (int i: UNIVERSES) {
        for (int i = 3; i <= 9; i++) {
            int newBPos = (state.bPos() + i - 1) % MAX_POS + 1;
            long newBScore = state.scoreB() + newBPos;

            wins = wins.accumulateWins(
                    recurse(new RecursionState(Turns.A_TURN, state.aPos(), newBPos, state.scoreA(), newBScore)),
                    UNIVERSE_COUNTS[i]);
        }
        winsFromState.put(state, wins);
        return wins;
    }

}
