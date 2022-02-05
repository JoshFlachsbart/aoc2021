package com.bayer.aoc2021.day19;

import com.bayer.aoc2021.Utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Scanner {
    public int scannerNumber;
    public List<Probe> probes;
    public Probe.ProbeOffset offset = new Probe.ProbeOffset(0,0,0);
    public Probe.Facing facing;
    public Probe.Orient orientation;
    public long bestMatch = 0;
    public static long MIN_MATCH = 12;

    public Scanner(int scannerNumber) {
        this.scannerNumber = scannerNumber;
        probes = new ArrayList<>();
    }

    public List<Probe> orientProbes(Probe.Facing facing, Probe.Orient orient) {
        return probes.stream()
                .map(p -> p.orient(orient))
                .map(p -> p.face(facing))
                .toList();
    }

    public boolean findBestMatch() {
        //Try each facing and orientation
        for (Probe.Orient orientation:  Probe.Orient.values()){
            for (Probe.Facing facing : Probe.Facing.values()) {
                List<Probe> orientedProbes = orientProbes(facing, orientation);
                if (tryOrientation(orientedProbes)) {
                    this.facing = facing;
                    this.orientation = orientation;
                    return true;
                }
            }
        }
        return false;
    }

    // Given two sets, find the best match, pairwise comparing each point as a possible origin.
    public boolean tryOrientation(List<Probe> orientedProbes) {
        for (Probe fromKey: orientedProbes) {
            for (Probe toKey: Probe.allFoundProbes()) {
                Probe.ProbeOffset offset = fromKey.calcOffset(toKey);
                long count = countOverlapWithOffset(orientedProbes, offset);
                if (count >= MIN_MATCH && count > bestMatch) {
                    bestMatch = count;
                    this.offset = offset;
                    return true;
                }
            }
        }
        return false;
    }

    // Given an offset, see how many probes in both sets match.
    public static long countOverlapWithOffset(List<Probe> compareFrom, Probe.ProbeOffset compareOffset) {
        return compareFrom.stream()
                .map(p -> p.offset(compareOffset))
                .filter(Probe.allFoundProbes()::contains)
                .count();
    }


    public List<Probe> getProbes(Probe.Facing facing, Probe.Orient orientation) {
        List<Probe> probes = this.probes;
        if (facing != null && orientation != null) probes = orientProbes(facing, orientation);
        return probes.stream().map(p -> p.offset(offset)).toList();
    }

    public List<Probe> getProbes() {
        return getProbes(facing, orientation);
    }

    public void addProbesAsFound() {
        getProbes().forEach(Probe::addFoundProbe);
    }

    public String toString() {
        return "Scanner: " + scannerNumber + " " + offset + " f: " + facing + " o: " + orientation;
    }

    public void print() {
        System.out.println(this);
        getProbes().forEach(System.out::println);
    }

    public static Scanner readScanner(Iterator<String> lines) throws Exception {
        String header = lines.next();
        String[] parts = header.split(" ");
        if (parts.length != 4 && !parts[1].equals("scanner"))
            throw new Exception("Reading non scanner header");

        Scanner output = new Scanner(Integer.parseInt(parts[2]));

        boolean done = false;
        while (lines.hasNext() && !done) {
            String probeLine = lines.next();
            if (probeLine == null || probeLine.equals("")) {
                done = true;
            } else {
                String[] vals = probeLine.split(",");
                output.probes.add(new Probe(
                        Integer.parseInt(vals[0]),
                        Integer.parseInt(vals[1]),
                        Integer.parseInt(vals[2])));
            }
        }

        return output;
    }

    public static boolean DEBUG_ON = false;

    public static void locateAllScanners(List<Scanner> scanners) {
        while(!scanners.isEmpty()) {
            for (int i = 0; i < scanners.size(); ) {
                Scanner scanner = scanners.get(i);
                if(scanner.findBestMatch()) {
                    if (DEBUG_ON) System.out.println("Found " + scanner);
                    scanner.addProbesAsFound();
                    scanners.remove(i);
                } else {
                    i++;
                }
            }
        }
    }

    public static int findBiggestDist(List<Scanner> scannersForDist)
    {
        int biggestDist = 0;
        for(int i = 0; i < scannersForDist.size()-1; i++) {
            for (int j = i+1; j < scannersForDist.size(); j++) {
                int dist = scannersForDist.get(i).offset.manhattan(scannersForDist.get(j).offset);
                if (dist > biggestDist) biggestDist = dist;
            }
        }
        return biggestDist;
    }

    public static void main(String[] args) throws Exception {
        Path absolutePath = new Utils().getLocalPath("day19");
        Iterator<String> lines = Files.readAllLines(absolutePath).iterator();

        DEBUG_ON = true;

        List<Scanner> scanners = new ArrayList<>();
        while(lines.hasNext()) {
            scanners.add(Scanner.readScanner(lines));
        }
        List<Scanner> scannersCopy = new ArrayList<>(scanners);
        System.out.println("Loaded " + scanners.size() + " scanners");

        scanners.remove(0).addProbesAsFound();
        locateAllScanners(scanners);
        Probe.printFoundProbes();

        System.out.println("Biggest scanner dist: " + findBiggestDist(scannersCopy));
    }
}
