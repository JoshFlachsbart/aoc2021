package com.bayer.aoc2021.day11;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnergyMapTest {
    @Test
    void testUpdate() {
        String data = """
        111
        181
        111""";
        String o1 = """
        211
        181
        111""";
        String o2 = """
        211
        182
        111""";
        String o3 = """
        322
        293
        222""";
        String o4 = """
        544
        405
        444""";

        EnergyMap m = new EnergyMap();
        m.loadMap(data.lines().toList());
        m.incrementElement(0,0);
        Assertions.assertEquals(o1, m.printMap());
        m.incrementElement(2,1);
        Assertions.assertEquals(o2, m.printMap());
        m.update();
        Assertions.assertEquals(o3, m.printMap());
        m.update();
        Assertions.assertEquals(o4, m.printMap());

        String data2 = """
        119
        181
        111""";
        String o5 = """
        340
        304
        333""";

        m = new EnergyMap();
        m.loadMap(data2.lines().toList());
        m.update();
        Assertions.assertEquals(o5, m.printMap());

        String data3 = """
                5483143223
                2745854711
                5264556173
                6141336146
                6357385478
                4167524645
                2176841721
                6882881134
                4846848554
                5283751526""";
        String s1 = """
                6594254334
                3856965822
                6375667284
                7252447257
                7468496589
                5278635756
                3287952832
                7993992245
                5957959665
                6394862637""";
        String s2 = """
                8807476555
                5089087054
                8597889608
                8485769600
                8700908800
                6600088989
                6800005943
                0000007456
                9000000876
                8700006848""";
        String s3 = """
                0050900866
                8500800575
                9900000039
                9700000041
                9935080063
                7712300000
                7911250009
                2211130000
                0421125000
                0021119000""";

        m = new EnergyMap();
        m.loadMap(data3.lines().toList());
        m.update();
        Assertions.assertEquals(s1, m.printMap());
        m.update();
        Assertions.assertEquals(s2, m.printMap());
        m.update();
        Assertions.assertEquals(s3, m.printMap());

    }

    @Test
    void fullTest() {
        String data = """
                5483143223
                2745854711
                5264556173
                6141336146
                6357385478
                4167524645
                2176841721
                6882881134
                4846848554
                5283751526""";

        EnergyMap m = new EnergyMap();
        m.loadMap(data.lines().toList());
        Assertions.assertEquals(1656, m.sim(100));
    }

}