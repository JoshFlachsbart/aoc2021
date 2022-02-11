package com.bayer.aoc2021.day21;

import java.util.Scanner;

public class QuantumGamePlayer {
    public static class PositionMap {
        private final static int NUM_POS = 10;
        long[][] positionCount = new long[NUM_POS][NUM_POS];

        public PositionMap(boolean isStart)
        {
            for (int aScore = 0; aScore < NUM_POS; aScore++) {
                for (int bScore = 0; bScore < NUM_POS; bScore++) {
                    positionCount[aScore][bScore] = 0;
                }
            }
            if (isStart) positionCount[0][0] = 1;
        }

        public void print() {
            for (int aScore = 0; aScore < NUM_POS; aScore++) {
                for (int bScore = 0; bScore < NUM_POS; bScore++) {
                    System.out.print(" " + positionCount[aScore][bScore]);
                }
                System.out.print('\n');
            }
        }

        private void pileOnA(PositionMap p, int aPos, int bPos) {
            for (int i = 1; i <= 3; i++)
                p.positionCount[(aPos + i) % NUM_POS][bPos] += positionCount[aPos][bPos];
        }

        public PositionMap playerATurn() {
            PositionMap p = new PositionMap(false);

            for (int aPos = 0; aPos < NUM_POS; aPos++) {
                for (int bPos = 0; bPos < NUM_POS; bPos++) {
                    pileOnA(p, aPos, bPos);
                }
            }
            return p;
        }

        private void pileOnB(PositionMap p, int aPos, int bPos) {
            for (int i = 1; i <= 3; i++)
                p.positionCount[aPos][(bPos + i) % NUM_POS] += positionCount[aPos][bPos];
        }

        public PositionMap playerBTurn() {
            PositionMap p = new PositionMap(false);

            for (int aPos = 0; aPos < NUM_POS; aPos++) {
                for (int bPos = 0; bPos < NUM_POS; bPos++) {
                    pileOnB(p, aPos, bPos);
                }
            }
            return p;
        }
    }

    public static class ScoreMap {
        public static final int NUM_SCORES = 24;
        public static final int WIN_SCORE = 21;
        long[][] scoreCount = new long[NUM_SCORES][NUM_SCORES];

        public ScoreMap()
        {
            for (int aScore = 0; aScore < NUM_SCORES; aScore++) {
                for (int bScore = 0; bScore < NUM_SCORES; bScore++) {
                    scoreCount[aScore][bScore] = 0;
                }
            }
        }

        public long runningGameCount() {
            long count = 0;
            for (int aScore = 0; aScore < WIN_SCORE; aScore++) {
                for (int bScore = 0; bScore < WIN_SCORE; bScore++) {
                    count = scoreCount[aScore][bScore];
                }
            }
            return count;
        }

        public void print() {
            for (int aScore = 0; aScore < NUM_SCORES; aScore++) {
                for (int bScore = 0; bScore < NUM_SCORES; bScore++) {
                    System.out.print(" " + scoreCount[aScore][bScore]);
                    if (bScore == 20) System.out.print(" |");
                }
                System.out.print('\n');
                if (aScore == 20) System.out.println("-------------------------------------------");
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

    public static void main(String[] args) {
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
}
