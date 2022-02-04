package com.bayer.aoc2021.day19;

import java.util.*;
import java.util.stream.Collectors;

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

    public static List<Probe.ProbeOffset> probeOffsetsFrom(List<Probe> orientedProbes, Probe origin) {
        return orientedProbes.stream().map(probe -> probe.calcOffset(origin)).toList();
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

    public void print() {
        System.out.println("Scanner: " + scannerNumber + " " + offset + " f: " + facing + " o: " + orientation);
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

    public static void main(String[] args) {
        Map<Probe, List<Probe.ProbeOffset>> offsetsForProbeAsOrigin = new HashMap<>();
        List<Scanner> scanners;

    }
}
