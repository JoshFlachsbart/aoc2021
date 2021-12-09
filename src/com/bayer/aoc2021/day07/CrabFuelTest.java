package com.bayer.aoc2021.day07;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CrabFuelTest {

    @Test
    void calcTotalDists() {
        CrabFuel cf = new CrabFuel();
        cf.loadCrabs("1,2,3");
        Assertions.assertEquals(new CrabFuel.BestPosition(0,6), cf.calcTotalDists(0));
        Assertions.assertEquals(new CrabFuel.BestPosition(1,3), cf.calcTotalDists(1));
        Assertions.assertEquals(new CrabFuel.BestPosition(3,3), cf.calcTotalDists(3));
    }

    @Test
    void findBestPosition() {
        CrabFuel cf = new CrabFuel();
        cf.loadCrabs("1,2,3");
        Assertions.assertEquals(new CrabFuel.BestPosition(2,2), cf.findBestPosition(cf::calcTotalDists).get());
    }

    @Test
    void calcTotalDists2() {
        CrabFuel cf = new CrabFuel();
        cf.loadCrabs("1,2,3");
        Assertions.assertEquals(new CrabFuel.BestPosition(0,10), cf.calcTotalDists2(0));
        Assertions.assertEquals(new CrabFuel.BestPosition(1,4), cf.calcTotalDists2(1));
        Assertions.assertEquals(new CrabFuel.BestPosition(3,4), cf.calcTotalDists2(3));
    }

    @Test
    void findBestPosition2() {
        CrabFuel cf = new CrabFuel();
        cf.loadCrabs("1,2,3");
        Assertions.assertEquals(new CrabFuel.BestPosition(2,2), cf.findBestPosition(cf::calcTotalDists2).get());
    }
}