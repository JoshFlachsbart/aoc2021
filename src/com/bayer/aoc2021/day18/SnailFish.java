package com.bayer.aoc2021.day18;

import com.bayer.aoc2021.Utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.List;

public class SnailFish {
    public static class SnailNum {
        int leftVal = -1;
        int rightVal = -1;
        SnailNum leftNum = null;
        SnailNum rightNum = null;
        SnailNum parent = null;

        public boolean hasLeft() {
            return leftNum != null || leftVal >= 0;
        }

        public boolean hasRight() {
            return rightNum != null || rightVal >= 0;
        }

        private void addLeft(int val) throws Exception {
            if (parent != null) parent.addLeftUp(val, this);
        }

        private void addLeftUp(int val, SnailNum last) throws Exception {
            if (rightNum == last) {
                if (leftNum == null) leftVal += val;
                else leftNum.addLeftDown(val);
            } else if (leftNum == last) addLeft(val);
            else throw new Exception("Malformed snail number.");
        }

        private void addLeftDown(int val) throws Exception {
            if (rightVal >= 0) rightVal += val;
            else if (rightNum != null) rightNum.addLeftDown(val);
            else throw new Exception("Malformed snail number.");
        }

        private void addRight(int val) throws Exception {
            if (parent != null) parent.addRightUp(val, this);
        }

        private void addRightUp(int val, SnailNum last) throws Exception {
            if (leftNum == last) {
                if (rightNum == null) rightVal += val;
                else rightNum.addRightDown(val);
            } else if (rightNum == last) addRight(val);
            else throw new Exception("Malformed snail number.");
        }

        private void addRightDown(int val) throws Exception {
            if (leftVal >= 0) leftVal += val;
            else if (leftNum != null) leftNum.addRightDown(val);
            else throw new Exception("Malformed snail number.");
        }

        public SnailNum add(SnailNum sn) throws Exception {
            SnailNum sum = new SnailNum();
            sum.leftNum = this;
            sum.rightNum = sn;
            parent = sum;
            sn.parent = sum;

            // reduce!
            boolean notDone = true;
            while (notDone) {
                notDone = sum.explode(0) || sum.split();
            }

            return sum;
        }

        public String toString() {
            return "[" + (leftNum == null ? leftVal : leftNum.toString()) + ","
                    + (rightNum == null ? rightVal : rightNum.toString()) + "]";
        }

        public String toString(int depth) {
            return "" + depth + ": [" + (leftNum == null ? leftVal : leftNum.toString(depth + 1)) + ","
                    + (rightNum == null ? rightVal : rightNum.toString(depth + 1)) + "]";
        }

        public boolean equals(SnailNum n) {
            boolean isEqual = false;
            if (leftNum != null && n.leftNum != null) isEqual = leftNum.equals(n.leftNum);
            else isEqual = leftVal == n.leftVal;
            if (isEqual) {
                if (rightNum != null && n.rightNum != null) isEqual = rightNum.equals(n.rightNum);
                else isEqual = rightVal == n.rightVal;
            }
            return isEqual;
        }

        public boolean explode(int depth) throws Exception {
            boolean exploded = false;
            if (depth == 4) {
                if (leftNum != null) throw new Exception("Snail Num too deep.");
                addLeft(leftVal);
                if (rightNum != null) throw new Exception("Snail Num too deep.");
                addRight(rightVal);
                if (parent.leftNum == this) { parent.leftNum = null; parent.leftVal = 0; }
                if (parent.rightNum == this) { parent.rightNum = null; parent.rightVal = 0; }
                exploded = true;
            }
            if (!exploded && leftNum != null) exploded = leftNum.explode(depth + 1);
            if (!exploded && rightNum != null) exploded = rightNum.explode(depth + 1);
            return exploded;
        }

        private SnailNum newSplit(int oldVal, SnailNum parent) {
            SnailNum newNum = new SnailNum();
            newNum.leftVal = oldVal / 2;
            newNum.rightVal = oldVal - newNum.leftVal;
            newNum.parent = parent;
            return newNum;
        }

        public boolean split() {
            boolean splitted = false;
            if (leftNum != null) splitted = leftNum.split();
            else if (leftVal > 9) {
                splitted = true;
                leftNum = newSplit(leftVal, this);
                leftVal = -1;
            }
            if (!splitted) {
                if (rightNum != null) splitted = rightNum.split();
                else if (rightVal > 9) {
                    splitted = true;
                    rightNum = newSplit(rightVal, this);
                    rightVal = -1;
                }
            }
            return splitted;
        }

        public int magnitude() {
            int leftMag;
            int rightMag;
            if (leftNum != null) leftMag = leftNum.magnitude();
            else leftMag = leftVal;
            if (rightNum != null) rightMag = rightNum.magnitude();
            else rightMag = rightVal;
            return (3*leftMag) + (2*rightMag);
        }
    }

    private static SnailNum readSingleNum(SnailNum parent, CharacterIterator i) throws Exception {
        char c = i.next();
        SnailNum sn = new SnailNum();
        sn.parent = parent;
        if (c == '[') sn.leftNum = readSingleNum(sn, i);
        else sn.leftVal = Character.digit(c, 10);
        if (i.next() != ',') throw new Exception("Malformed Snail Num");
        c = i.next();
        if (c == '[') sn.rightNum = readSingleNum(sn, i);
        else sn.rightVal = Character.digit(c, 10);
        if (i.next() != ']') throw new Exception("Malformed Snail Num");
        return sn;
    }

    public static SnailNum read(String input) throws Exception {
        CharacterIterator i = new StringCharacterIterator(input);
        return readSingleNum(null, i);
    }

    public static SnailNum sumLines(List<String> lines) throws Exception {
        SnailNum num = null;
        for (String line: lines) {
            if (num == null) num = SnailFish.read(line);
            else num = num.add(SnailFish.read(line));
        }
        return num;
    }

    public static void main(String[] args) throws Exception {
        Path absolutePath = new Utils().getLocalPath("day18");
        List<String> lines = Files.readAllLines(absolutePath);
        SnailNum n = sumLines(lines);
        System.out.println(n);
        System.out.println(n.magnitude());
    }
}
