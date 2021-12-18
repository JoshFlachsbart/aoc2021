package com.bayer.aoc2021.day16;

import com.bayer.aoc2021.Utils;
import com.bayer.aoc2021.day03.BinaryNumber;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PacketDecoder {

    public static BinaryNumber deHex(String packet) {
        BinaryNumber binaryPacket = new BinaryNumber("");
        for(char c: packet.toCharArray())
            binaryPacket.appendHex(c);
        return binaryPacket;
    }

    public static long decodeVersion(BinaryNumber packet) {
        return packet.popBits(3).getVal();
    }

    public static Type decodeType(BinaryNumber packet) {
        return Type.lookup((int) packet.popBits(3).getVal());
    }

    public enum Type {
        SUM(0),
        PROD(1),
        MIN(2),
        MAX(3),
        LIT(4),
        GT(5),
        LT(6),
        EQ(7);

        private final int code;

        Type(int code) { this.code = code; }

        public static Type lookup(int code) {
            return Arrays.stream(values()).filter(v -> v.code == code).findFirst().orElseThrow(IllegalArgumentException::new);
        }
    }

    public static long decodeLiteral(BinaryNumber packet) {
        BinaryNumber accumulator = new BinaryNumber("");
        boolean readMore = true;
        while (readMore) {
            readMore = packet.popBits(1).getBit(0);
            accumulator.appendBits(packet.popBits(4));
        }
        return accumulator.getVal();
    }

    public static List<Packet> decodeOperatorSubpackets(BinaryNumber packet) {
        List<Packet> subPackets = new ArrayList<>();
        if (!packet.popBits(1).getBit(0)) {
            // next 15 bits is length
            BinaryNumber opLength = packet.popBits(15);
            int len = (int) opLength.getVal();
            BinaryNumber subPacketsCoded = packet.popBits(len);
            while (subPacketsCoded.getBitCount() > 0) {
                subPackets.add(decodePacket(subPacketsCoded));

            }
        } else {
            // next 11 bits are count.
            BinaryNumber opLength = packet.popBits(11);
            int count = (int) opLength.getVal();
            for (int i = 0; i < count; i++) {
                subPackets.add(decodePacket(packet));
            }
        }
        return subPackets;
    }

    public static abstract class Packet {
        Type type;
        long version;

        public Packet(long version, Type type) {
            this.type = type;
            this.version = version;
        }

        public long getVersionSum() {
            return version;
        }

        public abstract long getValue();

        public boolean equals(@NotNull Packet p) {
            return this.type == p.type
                    && this.version == p.version;
        }
    }

    public static class LiteralPacket extends Packet {
        long value;
        public LiteralPacket(long version, long value) {
            super(version, Type.LIT);
            this.value = value;
        }

        @Override
        public boolean equals(@NotNull Packet p) {
            return p instanceof LiteralPacket && ((LiteralPacket) p).value == this.value;
        }

        @Override
        public long getValue() {
            return value;
        }
    }

    public static class OperatorPacket extends Packet {
        List<Packet> subPackets;
        public OperatorPacket(long version, Type type, List<Packet> subPackets) {
            super(version, type);
            this.subPackets = subPackets;
        }

        @Override
        public long getVersionSum() {
            return version + subPackets.stream().map(Packet::getVersionSum).reduce(Long::sum).orElse(0L);
        }

        @Override
        public long getValue() {
            return switch (type) {
                case SUM -> subPackets.stream().map(Packet::getValue).reduce(Long::sum).orElseThrow(IllegalArgumentException::new);
                case PROD -> subPackets.stream().map(Packet::getValue).reduce((x, y) -> x * y).orElseThrow(IllegalArgumentException::new);
                case MIN -> subPackets.stream().map(Packet::getValue).min(Long::compare).orElseThrow(IllegalArgumentException::new);
                case MAX -> subPackets.stream().map(Packet::getValue).max(Long::compare).orElseThrow(IllegalArgumentException::new);
                case LT -> subPackets.get(0).getValue() < subPackets.get(1).getValue() ? 1 : 0;
                case GT -> subPackets.get(0).getValue() > subPackets.get(1).getValue() ? 1 : 0;
                case EQ -> subPackets.get(0).getValue() == subPackets.get(1).getValue() ? 1 : 0;
                default -> throw new IllegalArgumentException();  // Literals handled elsewhere
            };
        }
    }

    public static Packet decodePacket(BinaryNumber packet) {
        long version = decodeVersion(packet);
        Type type = decodeType(packet);
        Packet decodedPacket;
        if (type == Type.LIT) decodedPacket = new LiteralPacket(version, decodeLiteral(packet));
        else decodedPacket = new OperatorPacket(version, type, decodeOperatorSubpackets(packet));
        return decodedPacket;
    }

    public static void main(String[] args) throws Exception
    {
        BinaryNumber packetCode = PacketDecoder.deHex(Files.readString(new Utils().getLocalPath("day16")));
        Packet packet = PacketDecoder.decodePacket(packetCode);
        System.out.println(packet.getVersionSum());
        System.out.println(packet.getValue());

    }
}
