package com.bayer.aoc2021.day13;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FolderTest {
    @Test
    void flip() {
        Assertions.assertEquals(new Folder.Point(0,0),
                Folder.flipPointY(new Folder.Point(0,2),1));
        Assertions.assertEquals(new Folder.Point(0,0),
                Folder.flipPointY(new Folder.Point(0,6),3));
        Assertions.assertEquals(new Folder.Point(0,1),
                Folder.flipPointY(new Folder.Point(0,5),3));
        Assertions.assertEquals(new Folder.Point(0,2),
                Folder.flipPointY(new Folder.Point(0,4),3));
    }

    @Test
    void simpleFold() {
        List<Folder.Point> twoPoints = Arrays.asList(new Folder.Point(0,0), new Folder.Point(0, 2));
        Assertions.assertEquals(1, Folder.foldPointsY(twoPoints, 1).size());

        String input = """
                6,10
                0,14
                9,10
                0,3
                10,4
                4,11
                6,0
                6,12
                4,1
                0,13
                10,12
                3,4
                3,0
                8,4
                1,10
                2,14
                8,10
                9,0""";
        List<Folder.Point> points = input.lines().map(Folder.Point::parsePoint).toList();
        List<Folder.Point> firstFold = Folder.foldPointsY(points, 7);
        Assertions.assertEquals(17, firstFold.size());
        List<Folder.Point> secondFold = Folder.foldPointsX(points, 5);
        Assertions.assertEquals(17, secondFold.size());
    }
}