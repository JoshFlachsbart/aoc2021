package com.bayer.aoc2021.day06;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FishSimTest {

    @Test
    void step() {
        FishSim sim = new FishSim();
        sim.loadFish("3,4,3,1,2");
        Assertions.assertEquals(5, sim.step()); // one day
        sim.printFish();
        Assertions.assertEquals(6, sim.step()); // two days
        sim.printFish();
        Assertions.assertEquals(26, sim.steps(16)); // 18 days
        sim.printFish();
        Assertions.assertEquals(5934, sim.steps(62)); //80 days
    }

    @Test
    void step2() {
        FishSim2 sim = new FishSim2();
        sim.loadFish("3,4,3,1,2");
        Assertions.assertEquals(5, sim.step()); // one day
        Assertions.assertEquals(6, sim.step()); // two days
        Assertions.assertEquals(26, sim.steps(16)); // 18 days
        Assertions.assertEquals(5934, sim.steps(62)); //80 days
    }
}