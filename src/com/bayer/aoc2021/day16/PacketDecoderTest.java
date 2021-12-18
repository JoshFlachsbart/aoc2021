package com.bayer.aoc2021.day16;

import com.bayer.aoc2021.day03.BinaryNumber;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PacketDecoderTest {

    @Test
    void fullDecoder() {
        BinaryNumber packet = PacketDecoder.deHex("38006F45291200");
        Assertions.assertEquals(9, PacketDecoder.decodePacket(packet).getVersionSum());
        packet = PacketDecoder.deHex("EE00D40C823060");
        Assertions.assertEquals(14, PacketDecoder.decodePacket(packet).getVersionSum());
        packet = PacketDecoder.deHex("8A004A801A8002F478");
        Assertions.assertEquals(16, PacketDecoder.decodePacket(packet).getVersionSum());
        packet = PacketDecoder.deHex("620080001611562C8802118E34");
        Assertions.assertEquals(12, PacketDecoder.decodePacket(packet).getVersionSum());
        packet = PacketDecoder.deHex("C0015000016115A2E0802F182340");
        Assertions.assertEquals(23, PacketDecoder.decodePacket(packet).getVersionSum());
        packet = PacketDecoder.deHex("A0016C880162017C3686B18A3D4780");
        Assertions.assertEquals(31, PacketDecoder.decodePacket(packet).getVersionSum());
    }

    @Test
    void fullVaues() {
        BinaryNumber packet = PacketDecoder.deHex("C200B40A82");
        Assertions.assertEquals(3, PacketDecoder.decodePacket(packet).getValue());
        packet = PacketDecoder.deHex("04005AC33890");
        Assertions.assertEquals(54, PacketDecoder.decodePacket(packet).getValue());
        packet = PacketDecoder.deHex("880086C3E88112");
        Assertions.assertEquals(7, PacketDecoder.decodePacket(packet).getValue());
        packet = PacketDecoder.deHex("CE00C43D881120");
        Assertions.assertEquals(9, PacketDecoder.decodePacket(packet).getValue());
        packet = PacketDecoder.deHex("D8005AC2A8F0");
        Assertions.assertEquals(1, PacketDecoder.decodePacket(packet).getValue());
        packet = PacketDecoder.deHex("F600BC2D8F");
        Assertions.assertEquals(0, PacketDecoder.decodePacket(packet).getValue());
        packet = PacketDecoder.deHex("9C005AC2F8F0");
        Assertions.assertEquals(0, PacketDecoder.decodePacket(packet).getValue());
        packet = PacketDecoder.deHex("9C0141080250320F1802104A08");
        Assertions.assertEquals(1, PacketDecoder.decodePacket(packet).getValue());
    }

    @Test
    void literalDecoder() {
        BinaryNumber packet = PacketDecoder.deHex("D2");
        Assertions.assertEquals(6, PacketDecoder.decodeVersion(packet));
        Assertions.assertEquals(PacketDecoder.Type.LIT, PacketDecoder.decodeType(packet));

        BinaryNumber packet2 = PacketDecoder.deHex("D2FE28");
        Assertions.assertTrue(new PacketDecoder.LiteralPacket(6, 2021)
                .equals(PacketDecoder.decodePacket(packet2)));
    }

}