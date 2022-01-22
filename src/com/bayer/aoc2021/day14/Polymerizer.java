package com.bayer.aoc2021.day14;

import com.bayer.aoc2021.Utils;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Polymerizer {
    Map<String, Character> rules = new HashMap<>();

    public void loadRules(List<String> ruleStrings) {
        for (String rule: ruleStrings) {
            String[] parts = rule.split(" ");
            rules.put(parts[0], parts[2].charAt(0));
        }
    }

    public String runRules(String input) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length() - 1; i++) {
            String match = input.substring(i, i+2);
            sb.append(input.charAt(i));
            if (rules.containsKey(match)) sb.append(rules.get(match));
        }
        sb.append(input.charAt(input.length() - 1));
        return sb.toString();
    }

    public Map <String, Long> runRulesCounts(Map <String, Long> pairCounts) {
        Map <String, Long> outputCounts = new HashMap<>();
        for (String key: pairCounts.keySet()) {
            long currCount = pairCounts.get(key);
            String firstKey = "" + key.charAt(0) + rules.get(key);
            outputCounts.put(firstKey, currCount + outputCounts.getOrDefault(firstKey, 0L));
            String secondKey = "" + rules.get(key) + key.charAt(1);
            outputCounts.put(secondKey, currCount + outputCounts.getOrDefault(secondKey, 0L));
        }
        return outputCounts;
    }

    public String runRules(String input, int numTimes) {
        String output = input;
        for (int i = 0; i < numTimes; i++) {
            output = runRules(output);
        }
        return output;
    }

    public long runRulesCounts(String input, int numTimes) {
        Map <String, Long> pairCounts = new HashMap<>();
        for (int i = 0; i < input.length() - 1; i++) {
            String pair = input.substring(i, i+2);
            pairCounts.put(pair, pairCounts.getOrDefault(pair, 0L) + 1);
        }
        for (int i = 0; i < numTimes; i++) {
            pairCounts = runRulesCounts(pairCounts);
        }
        System.out.println(pairCounts);
        Map<Character, Long> counts = new HashMap<>();
        for (String key: pairCounts.keySet()) {
            char firstChar = key.charAt(0);
            char secondChar = key.charAt(1);
            long count = pairCounts.get(key);
            counts.put(firstChar, counts.getOrDefault(firstChar, 0L) + count);
            counts.put(secondChar, counts.getOrDefault(secondChar, 0L) + count);
        }
        // remove double of first and last letter!
        char firstChar = input.charAt(0);
        char secondChar = input.charAt(input.length() - 1);
        counts.put(firstChar, counts.getOrDefault(firstChar, 0L) - 1);
        counts.put(secondChar, counts.getOrDefault(secondChar, 0L) - 1);
        System.out.println(counts);
        long max = counts.values().stream().max(Long::compare).get();
        long min = counts.values().stream().min(Long::compare).get();
        return (max / 2L) - (min / 2L);
    }

    public static int calcStats(String input) {
        Map<Character, Integer> counts = new HashMap<>();
        for (char c: input.toCharArray()) {
            counts.put(c, counts.getOrDefault(c, 0) + 1);
        }
        System.out.println(counts);
        return counts.values().stream().max(Integer::compare).get() -
                counts.values().stream().min(Integer::compare).get();
    }

    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(new Utils().getLocalPath("day14"));
        String input = lines.remove(0); lines.remove(0);
        Polymerizer p = new Polymerizer();
        p.loadRules(lines);
        String output = p.runRules(input, 10);
        System.out.println(calcStats(output));
        System.out.println(p.runRulesCounts(input, 40));

    }
}
