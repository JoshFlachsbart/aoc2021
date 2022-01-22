package com.bayer.aoc2021.day18;

import com.bayer.aoc2021.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static com.bayer.aoc2021.day18.SnailFish.findMaxSum;
import static com.bayer.aoc2021.day18.SnailFish.readLines;

class SnailFishTest {
    static List<SnailFish.SnailNum> toNums(String lines) {
        return readLines(lines.lines().collect(Collectors.toList()));
    }

    @Test
    void add() throws Exception {
        SnailFish.SnailNum a = SnailFish.read("[2,3]");
        SnailFish.SnailNum b = SnailFish.read("[3,5]");
        Assertions.assertTrue(SnailFish.read("[[2,3],[3,5]]").equals(a.add(b)));
    }

    @Test
    void explode() throws Exception {
        SnailFish.SnailNum a = SnailFish.read("[[[[[9,8],1],2],3],4]");
        SnailFish.SnailNum explodedA = SnailFish.read("[[[[0,9],2],3],4]");
        a.explode(0);
        Assertions.assertTrue(explodedA.equals(a));
        SnailFish.SnailNum b = SnailFish.read("[7,[6,[5,[4,[3,2]]]]]");
        SnailFish.SnailNum explodedB = SnailFish.read("[7,[6,[5,[7,0]]]]");
        b.explode(0);
        Assertions.assertTrue(explodedB.equals(b));
        SnailFish.SnailNum c = SnailFish.read("[[6,[5,[4,[3,2]]]],1]");
        SnailFish.SnailNum explodedC = SnailFish.read("[[6,[5,[7,0]]],3]");
        c.explode(0);
        Assertions.assertTrue(explodedC.equals(c));
        SnailFish.SnailNum d = SnailFish.read("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]");
        SnailFish.SnailNum explodedD = SnailFish.read("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]");
        d.explode(0);
        Assertions.assertTrue(explodedD.equals(d));
        SnailFish.SnailNum e = SnailFish.read("[[[[6,6],[0,6]],[[6,6],[2,[6,7]]]],[[[0,2],[2,2]],[8,[8,1]]]]");
        SnailFish.SnailNum explodedE = SnailFish.read("[[[[6,6],[0,6]],[[6,6],[8,0]]],[[[7,2],[2,2]],[8,[8,1]]]]");
        e.explode(0);
        Assertions.assertTrue(explodedD.equals(d));
    }

    @Test
    void workedSolution() throws Exception {
        SnailFish.SnailNum a = SnailFish.read("[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]");
        SnailFish.SnailNum b = SnailFish.read("[[[[0,7],4],[7,[[8,4],9]]],[1,1]]");
        //SnailFish.SnailNum c = SnailFish.read("[[[[0,7],4],[15,[0,13]]],[1,1]]");
        //SnailFish.SnailNum d = SnailFish.read("[[[[0,7],4],[[7,8],[0,13]]],[1,1]]");
        SnailFish.SnailNum e = SnailFish.read("[[[[0,7],4],[[7,8],[0,[6,7]]]],[1,1]]");
        SnailFish.SnailNum f = SnailFish.read("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]");
        a.explode(0);
        Assertions.assertTrue(a.equals(b));
        a.explode(0);
        //Assertions.assertTrue(a.equals(c));
        a.split();
        //Assertions.assertTrue(a.equals(d));
        a.split();
        Assertions.assertTrue(a.equals(e));
        a.explode(0);
        Assertions.assertTrue(a.equals(f));
    }

    @Test
    void magnitude() {
        SnailFish.SnailNum a = SnailFish.read("[[1,2],[[3,4],5]]");
        Assertions.assertEquals(143, a.magnitude());
        SnailFish.SnailNum b = SnailFish.read("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]");
        Assertions.assertEquals(1384, b.magnitude());
        SnailFish.SnailNum c = SnailFish.read("[[[[3,0],[5,3]],[4,4]],[5,5]]");
        Assertions.assertEquals(791, c.magnitude());
        SnailFish.SnailNum d = SnailFish.read("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]");
        Assertions.assertEquals(3488, d.magnitude());
    }

    @Test
    void singleSums() throws Exception {
        SnailFish.SnailNum a = SnailFish.read("[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]");
        SnailFish.SnailNum b = SnailFish.read("[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]");
        Assertions.assertTrue(SnailFish.read("[[[[4,0],[5,4]],[[7,7],[6,0]]],[[8,[7,7]],[[7,9],[5,0]]]]").equals(a.add(b)));
        a = SnailFish.read("[[[[7,8],[6,7]],[[6,8],[0,8]]],[[[7,7],[5,0]],[[5,5],[5,6]]]]");
        b = SnailFish.read("[[[5,[7,4]],7],1]");
        Assertions.assertTrue(SnailFish.read("[[[[7,7],[7,7]],[[8,7],[8,7]]],[[[7,0],[7,7]],9]]").equals(a.add(b)));
        a = SnailFish.read("[[[[7,7],[7,8]],[[9,5],[8,7]]],[[[6,8],[0,8]],[[9,9],[9,0]]]]");
        b = SnailFish.read("[[2,[2,2]],[8,[8,1]]]");
        Assertions.assertTrue(SnailFish.read("[[[[6,6],[6,6]],[[6,0],[6,7]]],[[[7,7],[8,9]],[8,[8,1]]]]").equals(a.add(b)));
        a = SnailFish.read("[[[[7,0],[7,7]],[[7,7],[7,8]]],[[[7,7],[8,8]],[[7,7],[8,7]]]]");
        b = SnailFish.read("[7,[5,[[3,8],[1,4]]]]");
        Assertions.assertTrue(SnailFish.read("[[[[7,7],[7,8]],[[9,5],[8,7]]],[[[6,8],[0,8]],[[9,9],[9,0]]]]").equals(a.add(b)));
    }

    @Test
    void sumLines() throws Exception {
        String nums = """
                [1,1]
                [2,2]
                [3,3]
                [4,4]""";
        SnailFish.SnailNum a = SnailFish.sumLines(toNums(nums));
        Assertions.assertTrue(SnailFish.read("[[[[1,1],[2,2]],[3,3]],[4,4]]").equals(a));

        nums = """
                [1,1]
                [2,2]
                [3,3]
                [4,4]
                [5,5]""";
        a = SnailFish.sumLines(toNums(nums));
        Assertions.assertTrue(SnailFish.read("[[[[3,0],[5,3]],[4,4]],[5,5]]").equals(a));

        nums = """
                [1,1]
                [2,2]
                [3,3]
                [4,4]
                [5,5]
                [6,6]""";
        a = SnailFish.sumLines(toNums(nums));
        Assertions.assertTrue(SnailFish.read("[[[[5,0],[7,4]],[5,5]],[6,6]]").equals(a));

        nums = """
                [[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]
                [7,[[[3,7],[4,3]],[[6,3],[8,8]]]]
                [[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]
                [[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]
                [7,[5,[[3,8],[1,4]]]]
                [[2,[2,2]],[8,[8,1]]]
                [2,9]
                [1,[[[9,3],9],[[9,0],[0,7]]]]
                [[[5,[7,4]],7],1]
                [[[[4,2],2],6],[8,7]]""";
        a = SnailFish.sumLines(toNums(nums));
        Assertions.assertTrue(SnailFish.read("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]").equals(a));
        Assertions.assertEquals(3488, a.magnitude());

        nums = """
                [[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
                [[[5,[2,8]],4],[5,[[9,9],0]]]
                [6,[[[6,2],[5,6]],[[7,6],[4,7]]]]
                [[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]
                [[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]
                [[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]
                [[[[5,4],[7,7]],8],[[8,3],8]]
                [[9,3],[[9,9],[6,[4,9]]]]
                [[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]
                [[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]""";
        a = SnailFish.sumLines(toNums(nums));
        Assertions.assertTrue(SnailFish.read("[[[[6,6],[7,6]],[[7,7],[7,0]]],[[[7,7],[7,7]],[[7,8],[9,9]]]]").equals(a));
        Assertions.assertEquals(4140, a.magnitude());

    }

    @Test
    void answer() throws Exception {
        Path absolutePath = new Utils().getLocalPath("day18");
        List<String> lines = Files.readAllLines(absolutePath);
        List<SnailFish.SnailNum> nums = SnailFish.readLines(lines);
        Assertions.assertEquals(4111, SnailFish.sumLines(nums).magnitude());
        Assertions.assertEquals(4917, SnailFish.findMaxSum(lines));
    }

}