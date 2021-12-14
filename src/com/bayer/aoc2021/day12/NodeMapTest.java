package com.bayer.aoc2021.day12;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NodeMapTest {

    @Test
    void getOrAdd() {
        NodeMap nm = new NodeMap();
        Assertions.assertTrue(nm.getOrAdd("A").bigNode);
        Assertions.assertEquals(1, nm.nodeList.size());
    }

    @Test
    void addEdge() {
        NodeMap nm = new NodeMap();
        nm.addEdge("a", "b");
        Assertions.assertEquals(2, nm.nodeList.size());
        Assertions.assertEquals("a", nm.getOrAdd("a").name);
        Assertions.assertEquals("b", nm.getOrAdd("b").name);
    }

    @Test
    void countPaths() {
        NodeMap nm = new NodeMap();
        String data = """
                start-A
                start-b
                A-c
                A-b
                b-d
                A-end
                b-end""";
        nm.addEdges(data.lines().toList());
        Assertions.assertEquals(10, nm.countPaths());

        nm = new NodeMap();
        String data2 = """
                fs-end
                he-DX
                fs-he
                start-DX
                pj-DX
                end-zg
                zg-sl
                zg-pj
                pj-he
                RW-he
                fs-DX
                pj-RW
                zg-RW
                start-pj
                he-WI
                zg-he
                pj-fs
                start-RW""";
        nm.addEdges(data2.lines().toList());
        Assertions.assertEquals(226, nm.countPaths());
    }

    @Test
    void countPaths2() {
        NodeMap nm = new NodeMap();
        String data = """
                start-A
                start-b
                A-c
                A-b
                b-d
                A-end
                b-end""";
        nm.addEdges(data.lines().toList());
        Assertions.assertEquals(36, nm.countPaths2());

        nm = new NodeMap();
        String data2 = """
                fs-end
                he-DX
                fs-he
                start-DX
                pj-DX
                end-zg
                zg-sl
                zg-pj
                pj-he
                RW-he
                fs-DX
                pj-RW
                zg-RW
                start-pj
                he-WI
                zg-he
                pj-fs
                start-RW""";
        nm.addEdges(data2.lines().toList());
        Assertions.assertEquals(3509, nm.countPaths2());
    }
}