package com.bayer.aoc2021.day10;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NavSyntaxTest {
    @Test
    void checkError() {
        Assertions.assertFalse(NavSyntax.findError("()").isPresent());
        Assertions.assertFalse(NavSyntax.findError("(<>)").isPresent());
        Assertions.assertEquals(')', NavSyntax.findError("({)").get());
        Assertions.assertEquals('>', NavSyntax.findError("(<(()<>{}>)").get());
    }
}