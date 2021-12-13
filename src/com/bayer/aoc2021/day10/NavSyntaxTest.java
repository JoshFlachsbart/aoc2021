package com.bayer.aoc2021.day10;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class NavSyntaxTest {
    @Test
    void checkError() {
        Assertions.assertFalse(NavSyntax.findError("()").isPresent());
        Assertions.assertFalse(NavSyntax.findError("(<>)").isPresent());
        Assertions.assertEquals(')', NavSyntax.findError("({)").get());
        Assertions.assertEquals('>', NavSyntax.findError("(<(()<>{}>)").get());
    }

    @Test
    void checkAutocomplete() {
        Assertions.assertEquals(toCharList("}}]])})]"), NavSyntax.autoComplete("[({(<(())[]>[[{[]{<()<>>"));
        Assertions.assertEquals(toCharList(")}>]})"), NavSyntax.autoComplete("[(()[<>])]({[<{<<[]>>("));
        Assertions.assertEquals(toCharList("}}>}>))))"), NavSyntax.autoComplete("(((({<>}<{<{<>}{[]{[]{}"));
        Assertions.assertEquals(toCharList("]]}}]}]}>"), NavSyntax.autoComplete("{<[[]]>}<{[{[{[]{()[[[]"));
        Assertions.assertEquals(toCharList("])}>"), NavSyntax.autoComplete("<{([{{}}[<[[[<>{}]]]>[]]"));
    }

    private List<Character> toCharList(String str) {
        return str.chars().mapToObj(c -> (char)c).collect(Collectors.toList());
    }

    @Test
    void checkAutocompleteScore() {
        Assertions.assertEquals(288957, NavSyntax.getAutocompleteVal(toCharList("}}]])})]")));
        Assertions.assertEquals(5566, NavSyntax.getAutocompleteVal(toCharList(")}>]})")));
        Assertions.assertEquals(1480781, NavSyntax.getAutocompleteVal(toCharList("}}>}>))))")));
        Assertions.assertEquals(995444, NavSyntax.getAutocompleteVal(toCharList("]]}}]}]}>")));
        Assertions.assertEquals(294, NavSyntax.getAutocompleteVal(toCharList("])}>")));
    }

}