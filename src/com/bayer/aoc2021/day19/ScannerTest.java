package com.bayer.aoc2021.day19;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

class ScannerTest {
    String file = """
            --- scanner 0 ---
            0,2,0
            4,1,0
            3,3,0
                            
            --- scanner 1 ---
            -1,-1,0
            -5,0,0
            -2,1,0
            """;

    String largerFile = """
            --- scanner 0 ---
            404,-588,-901
            528,-643,409
            -838,591,734
            390,-675,-793
            -537,-823,-458
            -485,-357,347
            -345,-311,381
            -661,-816,-575
            -876,649,763
            -618,-824,-621
            553,345,-567
            474,580,667
            -447,-329,318
            -584,868,-557
            544,-627,-890
            564,392,-477
            455,729,728
            -892,524,684
            -689,845,-530
            423,-701,434
            7,-33,-71
            630,319,-379
            443,580,662
            -789,900,-551
            459,-707,401
                        
            --- scanner 1 ---
            686,422,578
            605,423,415
            515,917,-361
            -336,658,858
            95,138,22
            -476,619,847
            -340,-569,-846
            567,-361,727
            -460,603,-452
            669,-402,600
            729,430,532
            -500,-761,534
            -322,571,750
            -466,-666,-811
            -429,-592,574
            -355,545,-477
            703,-491,-529
            -328,-685,520
            413,935,-424
            -391,539,-444
            586,-435,557
            -364,-763,-893
            807,-499,-711
            755,-354,-619
            553,889,-390
                        
            --- scanner 2 ---
            649,640,665
            682,-795,504
            -784,533,-524
            -644,584,-595
            -588,-843,648
            -30,6,44
            -674,560,763
            500,723,-460
            609,671,-379
            -555,-800,653
            -675,-892,-343
            697,-426,-610
            578,704,681
            493,664,-388
            -671,-858,530
            -667,343,800
            571,-461,-707
            -138,-166,112
            -889,563,-600
            646,-828,498
            640,759,510
            -630,509,768
            -681,-892,-333
            673,-379,-804
            -742,-814,-386
            577,-820,562
                        
            --- scanner 3 ---
            -589,542,597
            605,-692,669
            -500,565,-823
            -660,373,557
            -458,-679,-417
            -488,449,543
            -626,468,-788
            338,-750,-386
            528,-832,-391
            562,-778,733
            -938,-730,414
            543,643,-506
            -524,371,-870
            407,773,750
            -104,29,83
            378,-903,-323
            -778,-728,485
            426,699,580
            -438,-605,-362
            -469,-447,-387
            509,732,623
            647,635,-688
            -868,-804,481
            614,-800,639
            595,780,-596
                        
            --- scanner 4 ---
            727,592,562
            -293,-554,779
            441,611,-461
            -714,465,-776
            -743,427,-804
            -660,-479,-426
            832,-632,460
            927,-485,-438
            408,393,-506
            466,436,-512
            110,16,151
            -258,-428,682
            -393,719,612
            -211,-452,876
            808,-476,-593
            -575,615,604
            -485,667,467
            -680,325,-822
            -627,-443,-432
            872,-547,-609
            833,512,582
            807,604,487
            839,-516,451
            891,-625,532
            -652,-548,-490
            30,-46,-14
            """;

    @Test
    void readerTest() throws Exception {
        Iterator<String> linterator = file.lines().collect(Collectors.toList()).iterator();

        Scanner scanner0 = Scanner.readScanner(linterator);
        Assertions.assertEquals(0, scanner0.scannerNumber);
        Assertions.assertEquals(3, scanner0.probes.size());
        Assertions.assertEquals(new Probe(0, 2, 0), scanner0.probes.get(0));
        Scanner scanner1 = Scanner.readScanner(linterator);
        Assertions.assertEquals(1, scanner1.scannerNumber);
        Assertions.assertEquals(3, scanner1.probes.size());
        Assertions.assertEquals(new Probe(-1, -1, 0), scanner1.probes.get(0));
    }

    @Test
    void offsetCompareTest() {
        Probe.addFoundProbe(new Probe(1, 2, 3));
        Probe.addFoundProbe(new Probe(2, 1, 4));
        Probe.addFoundProbe(new Probe(-1, -1, -1));
        Probe.addFoundProbe(new Probe(0, 0, 0));
        List<Probe> compareFrom = Arrays.asList(
                new Probe(3, 0, 3),
                new Probe(-3, -1, 4),
                new Probe(1, -3, -1),
                new Probe(2, -2, 0));
        Assertions.assertEquals(3L, Scanner.countOverlapWithOffset(compareFrom, compareFrom.get(0).calcOffset(new Probe(1, 2, 3))));
    }

    @Test
    void offsetTest() throws Exception {
        Iterator<String> linterator = file.lines().collect(Collectors.toList()).iterator();

        Scanner scanner0 = Scanner.readScanner(linterator);
        Scanner scanner1 = Scanner.readScanner(linterator);

        // Add all probes from scanner 0 as found;
        scanner0.addProbesAsFound();
        Assertions.assertEquals(3, Probe.foundProbeCount());

        Scanner.MIN_MATCH = 2;
        Assertions.assertTrue(scanner1.tryOrientation(scanner1.probes));
        Assertions.assertEquals(3L, scanner1.bestMatch);
        Assertions.assertEquals(new Probe.ProbeOffset(5, 2, 0), scanner1.offset);
    }

    @Test
    void orientTest() throws Exception {
        Iterator<String> linterator = file.lines().collect(Collectors.toList()).iterator();

        Scanner scanner0 = Scanner.readScanner(linterator);

        Iterator<Probe> expected = Arrays.asList(
                new Probe(0, 2, 0),
                new Probe(0, 1, 4),
                new Probe(0, 3, 3)).iterator();

        Iterator<Probe> real = scanner0.orientProbes(Probe.Facing.Z, Probe.Orient.R0).iterator();
        while (expected.hasNext()) {
            Assertions.assertEquals(expected.next(), real.next());
        }

        expected = Arrays.asList(
                new Probe(-2, 0, 0),
                new Probe(-1, 0, 4),
                new Probe(-3, 0, 3)).iterator();

        real = scanner0.orientProbes(Probe.Facing.Z, Probe.Orient.R1).iterator();
        while (expected.hasNext()) {
            Assertions.assertEquals(expected.next(), real.next());
        }
    }

    @Test
    void bigTest() throws Exception {
        Iterator<String> linterator = largerFile.lines().collect(Collectors.toList()).iterator();

        Scanner scanner0 = Scanner.readScanner(linterator);
        Scanner scanner1 = Scanner.readScanner(linterator);
        Scanner scanner2 = Scanner.readScanner(linterator);
        Scanner scanner3 = Scanner.readScanner(linterator);
        Scanner scanner4 = Scanner.readScanner(linterator);

        scanner0.addProbesAsFound();
        //scanner0.print();

        Assertions.assertTrue(scanner1.findBestMatch());
        Assertions.assertEquals(12, scanner1.bestMatch);
        scanner1.print();
        Assertions.assertEquals(new Probe.ProbeOffset(68, -1246, -43), scanner1.offset);
        Assertions.assertEquals(Probe.Facing.NX, scanner1.facing);
        Assertions.assertEquals(Probe.Orient.R2, scanner1.orientation);

        System.out.println("Full list");
        Probe.printFoundProbes();
        Assertions.assertTrue(scanner4.findBestMatch());
        Assertions.assertEquals(12, scanner4.bestMatch);
        scanner4.print();
        Assertions.assertEquals(new Probe.ProbeOffset(-20,-1133,1061), scanner4.offset);
        //Assertions.assertEquals(Probe.Facing.NX, scanner4.facing);
        //Assertions.assertEquals(Probe.Orient.R2, scanner4.orientation);

        Assertions.assertTrue(scanner2.findBestMatch());
        Assertions.assertEquals(12, scanner2.bestMatch);
        Assertions.assertEquals(new Probe.ProbeOffset(1105,-1205,1229), scanner2.offset);
        //Assertions.assertEquals(Probe.Facing.NX, scanner1.facing);
        //Assertions.assertEquals(Probe.Orient.R2, scanner1.orientation);
        scanner2.print();

        Assertions.assertTrue(scanner3.findBestMatch());
        Assertions.assertEquals(12, scanner3.bestMatch);
        Assertions.assertEquals(new Probe.ProbeOffset(-92,-2380,-20), scanner3.offset);
        //Assertions.assertEquals(Probe.Facing.NX, scanner1.facing);
        //Assertions.assertEquals(Probe.Orient.R2, scanner1.orientation);
        scanner3.print();
    }
}