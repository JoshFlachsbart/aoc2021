package com.bayer.aoc2021.day14;

import com.bayer.aoc2021.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class PolymerizerTest {
    String ruleString = """
                CH -> B
                HH -> N
                CB -> H
                NH -> C
                HB -> C
                HC -> B
                HN -> C
                NN -> C
                BH -> H
                NC -> B
                NB -> B
                BN -> B
                BB -> N
                BC -> B
                CC -> N
                CN -> C""";
    @Test
    void runRules() {
        Polymerizer p = new Polymerizer();
        p.loadRules(ruleString.lines().toList());
        Assertions.assertEquals("NCNBCHB", p.runRules("NNCB"));
        Assertions.assertEquals("NBCCNBBBCBHCB", p.runRules("NCNBCHB"));
        Assertions.assertEquals("NBBBCNCCNBBNBNBBCHBHHBCHB", p.runRules("NBCCNBBBCBHCB"));
        Assertions.assertEquals("NBBNBNBBCCNBCNCCNBBNBBNBBBNBBNBBCBHCBHHNHCBBCBHCB", p.runRules("NBBBCNCCNBBNBNBBCHBHHBCHB"));
    }

    @Test
    void calcStats() {
        Assertions.assertEquals(18, Polymerizer.calcStats("NBBNBNBBCCNBCNCCNBBNBBNBBBNBBNBBCBHCBHHNHCBBCBHCB"));
        Polymerizer p = new Polymerizer();
        p.loadRules(ruleString.lines().toList());
        String output = p.runRules("NNCB", 10);
        Assertions.assertEquals(1588, Polymerizer.calcStats(output));

    }
    @Test
    void calcStats2() {
        Polymerizer p = new Polymerizer();
        p.loadRules(ruleString.lines().toList());
        Assertions.assertEquals(1588, p.runRulesCounts("NNCB", 10));

    }

    @Test
    void runRules2() {
        Polymerizer p = new Polymerizer();
        p.loadRules(ruleString.lines().toList());
        Assertions.assertEquals(1, p.runRulesCounts("NNCB", 1));
        Assertions.assertEquals(4, p.runRulesCounts("NCNBCHB", 1));
        Assertions.assertEquals(6, p.runRulesCounts("NBCCNBBBCBHCB", 1));
        Assertions.assertEquals(17, p.runRulesCounts("NBBBCNCCNBBNBNBBCHBHHBCHB", 1));
        Assertions.assertEquals(2188189693529L, p.runRulesCounts("NNCB", 40));
    }

    @Test
    void answer() throws Exception {
        List<String> lines = Files.readAllLines(new Utils().getLocalPath("day14"));
        String input = lines.remove(0); lines.remove(0);
        Polymerizer p = new Polymerizer();
        p.loadRules(lines);
        String output = p.runRules(input, 10);
        Assertions.assertEquals(3213, Polymerizer.calcStats(output));
        Assertions.assertEquals( 3711743744429L, p.runRulesCounts(input, 40));
    }


}