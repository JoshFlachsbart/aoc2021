package com.bayer.aoc2021.day08;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DigitDecoderTest {

    @Test
    public void letterCounter() {
        DigitDecoder dc = new DigitDecoder();
        Assertions.assertEquals(4,
                dc.letterCounter(List.of("1", "12", "123", "1234", "12345", "123456", "1234567")));
        Assertions.assertEquals(0,
                dc.letterCounter(List.of("1", "12345", "123456")));
        Assertions.assertEquals(3,
                dc.letterCounter(List.of("1", "12", "1", "12", "1", "12", "123456")));
    }

    @Test
    public void digitFinder() {
        DigitDecoder dc = new DigitDecoder();
        dc.found(7, "abc");
        Assertions.assertEquals("abc", dc.get(7));
        Assertions.assertEquals(7, dc.get("abc"));
        Assertions.assertEquals(7, dc.get("bca"));
        dc.found(8, "ebfagdc");
        Assertions.assertEquals("abcdefg", dc.get(8));
        Assertions.assertEquals(8, dc.get("abcdefg"));
        Assertions.assertEquals(8, dc.get("dbefcag"));
    }

    @Test
    public void digitLoader() {
        DigitDecoder dc = new DigitDecoder();
        dc.loadDigits("acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab".split(" "));
        Assertions.assertEquals("ab", dc.get(1));
        Assertions.assertEquals("abd", dc.get(7));
        Assertions.assertEquals(5, dc.get("cdfeb"));
        Assertions.assertEquals(3, dc.get("fcadb"));
        Assertions.assertEquals(5, dc.get("cdfeb"));
        Assertions.assertEquals(3, dc.get("cdbaf"));
    }

}