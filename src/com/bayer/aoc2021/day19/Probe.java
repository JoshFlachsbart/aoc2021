package com.bayer.aoc2021.day19;

import java.util.*;

public record Probe(int x, int y, int z) {
     Probe face(Facing facing) {
        return switch (facing) {
            case X -> this;
            case NX -> new Probe(-x(), y(), -z());
            case Y -> new Probe(-y(), x(), z());
            case NY -> new Probe(y(), -x(), z());
            case Z -> new Probe(-z(), y(), x());
            case NZ -> new Probe(z(), y(), -x());
        };
    }

    // Rotate around x.  Need to do this before facing.
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
        public int manhattan(ProbeOffset p2) {
            return Math.abs(dX() - p2.dX()) +
                    Math.abs(dY() - p2.dY()) +
                    Math.abs(dZ() - p2.dZ());
        }
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

    private final static List<Probe> foundProbesWithOffsets = new ArrayList<>();

    public static int foundProbeCount() {
        return foundProbesWithOffsets.size();
    }

    public static List<Probe> allFoundProbes() {
        return foundProbesWithOffsets;
    }

    public static void printFoundProbes() {
        System.out.println("Found " + foundProbeCount() + " probes: ");
        foundProbesWithOffsets.forEach(System.out::println);
    }

    public static void addFoundProbe(Probe absoluteFoundProbe) {
        if (!foundProbesWithOffsets.contains(absoluteFoundProbe)) {
            foundProbesWithOffsets.add(absoluteFoundProbe);
        }
    }
}
