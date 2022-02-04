package com.bayer.aoc2021.day19;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public record Probe(int x, int y, int z) {
     Probe face(Facing facing) {
        return switch (facing) {
            case X -> this;
            case NX -> new Probe(-x(), -y(), z());
            case NY -> new Probe(y(), -x(), z());
            case Y -> new Probe(-y(), x(), z());
            case Z -> new Probe(-z(), y(), x());
            case NZ -> new Probe(z(), y(), -x());
        };
    }

    Probe orient(Orient orientation) {
        return switch (orientation) {
            case R0 -> this;
            case R1 -> new Probe(x(), -z(), y());
            case R2 -> new Probe(x(), -y(), -z());
            case R3 -> new Probe(x(), z(), -y());
        };
    }

    // Each cardinal direction in positive and negative directions.
    public enum Facing {
        X,
        Y,
        Z,
        NX,
        NY,
        NZ
    }

    // Rotating around the cardinal direction clockwise.
    public enum Orient {
        R0,
        R1,
        R2,
        R3
    }

    public record ProbeOffset(int dX, int dY, int dZ) {
    }

    public ProbeOffset calcOffset(Probe origin) {
        return new ProbeOffset(
                origin.x() - x(),
                origin.y() - y(),
                origin.z() - z());
    }

    public Probe offset(ProbeOffset originOffset) {
        return new Probe(
                x() + originOffset.dX(),
                y() + originOffset.dY(),
                z() + originOffset.dZ());
    }

    private final static Map<Probe, List<ProbeOffset>> foundProbesWithOffsets = new HashMap<>();

    public static long compareOffsetLists(List<ProbeOffset> a, List<ProbeOffset> b) {
        return a.stream().filter(b::contains).count();
    }

    public static int foundProbeCount() {
        return foundProbesWithOffsets.size();
    }

    public static Set<Probe> allFoundProbes() {
        return foundProbesWithOffsets.keySet();
    }

    public static List<ProbeOffset> foundProbeOffsets(Probe p) {
        return foundProbesWithOffsets.get(p);
    }

    public static void printFoundProbes() {
        foundProbesWithOffsets.keySet().forEach(System.out::println);
    }

    public static void addFoundProbe(Probe absoluteFoundProbe) {
        if (!foundProbesWithOffsets.containsKey(absoluteFoundProbe)) {
            // Add probe offset to existing offset lists.
            Set<Probe> origins = foundProbesWithOffsets.keySet();
            for (Probe origin: origins) {
                foundProbesWithOffsets.get(origin).add(absoluteFoundProbe.calcOffset(origin));
            }
            // Add found point and calc all offsets.
            List<Probe.ProbeOffset> offsets = foundProbesWithOffsets.keySet()
                    .stream().map(p -> p.calcOffset(absoluteFoundProbe))
                    .collect(Collectors.toList());
            foundProbesWithOffsets.put(absoluteFoundProbe, offsets);
        }
    }
}
