package com.bayer.aoc2021.day09;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LocalMinimumTest {

    @Test
    void findMinima() {
        LocalMinimum lm = new LocalMinimum();
        lm.loadMatrices(List.of(
                "123",
                "416",
                "785"
        ));
        Assertions.assertEquals(10, lm.findMinima());
    }

    @Test
    void fill() {
        LocalMinimum lm = new LocalMinimum();
        lm.loadMatrices(List.of(
                "2199943210",
                "3987894921",
                "9856789892",
                "8767896789",
                "9899965678"
        ));
        Assertions.assertEquals(15, lm.findMinima());
    }

    @Test
    void dayOne() throws Exception {
        LocalMinimum lm = new LocalMinimum();
        lm.loadMatrices(Files.readAllLines(lm.getLocalPath("data.txt")));
        System.out.println("Score: " + lm.findMinima());
    }
}