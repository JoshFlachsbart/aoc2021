package com.bayer.aoc2021.day05;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VentMapTest {

    @Test
    void set() {
        VentMap map = new VentMap(2);
        map.set(0,0);
        Assertions.assertEquals(1, map.get(0,0));
        Assertions.assertEquals(0, map.get(1,1));
    }

    @Test
    void loadLine() {
        VentMap map = new VentMap(2);
        map.loadLine(new int[] { 0,0 }, new int[] {0,1});
        Assertions.assertEquals(1, map.get(0,0));
        Assertions.assertEquals(0, map.get(1,0));
        Assertions.assertEquals(1, map.get(0,1));
        Assertions.assertEquals(0, map.get(1,1));
        map = new VentMap(3);
        map.loadLine(new int[] { 0,2 }, new int[] {2,0});
        Assertions.assertEquals(0, map.get(0,0));
        Assertions.assertEquals(1, map.get(0,2));
        Assertions.assertEquals(1, map.get(1,1));
        Assertions.assertEquals(0, map.get(1,0));
        Assertions.assertEquals(1, map.get(2,0));
    }

    @Test
    void parseAndLoadLine() {
        VentMap map = new VentMap(2);
        map.parseAndLoadLine("0,0 -> 0,1");
        Assertions.assertEquals(1, map.get(0,0));
        Assertions.assertEquals(0, map.get(1,0));
        Assertions.assertEquals(1, map.get(0,1));
        Assertions.assertEquals(0, map.get(1,1));
    }
}