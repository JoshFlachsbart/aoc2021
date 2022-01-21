package com.bayer.aoc2021.day13;

import com.bayer.aoc2021.Point;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class FolderTest {
    @Test
    void simpleFold() {
        List<Point> twoPoints = Arrays.asList(new Point(0,0), new Point(0, 2));
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
        List<Point> points = input.lines().map(Point::parsePoint).toList();
        List<Point> firstFold = Folder.foldPointsY(points, 7);
        Assertions.assertEquals(17, firstFold.size());
        List<Point> secondFold = Folder.foldPointsX(points, 5);
        Assertions.assertEquals(17, secondFold.size());
    }
}