package com.bayer.aoc2021.day03;

import java.util.Iterator;

public class BinaryNumber implements Iterable<Boolean> {
    String binaryValue = "";

    public BinaryNumber(String value) {
        binaryValue = value;
    }

    public BinaryNumber() {

    }

    public long getVal() {
        return Long.parseLong(binaryValue, 2);
    }

    public boolean getBit(int index) {
        boolean isSet = false;
        if (index < binaryValue.length()) {
            isSet = binaryValue.charAt(index) == '1';
        }
        return isSet;
    }

    public int getBitCount() { return binaryValue.length(); }

    public void appendBit(boolean bit) {
        binaryValue = binaryValue + (bit ? '1' : '0');
    }

    public void appendBits(BinaryNumber toAppend) {
        binaryValue = binaryValue + toAppend.binaryValue;
    }

    public void appendHex(char hex) {
        int digit = Character.digit(hex, 16);
        String padding = "";
        if (digit < 2) padding += "0";
        if (digit < 4) padding += "0";
        if (digit < 8) padding += "0";
        binaryValue = binaryValue + padding + Integer.toBinaryString(digit);
    }

    @Override
    public Iterator<Boolean> iterator() {
        return new BIterator();
    }

    public BinaryNumber popBits(int i) {
        BinaryNumber popped =  new BinaryNumber(binaryValue.substring(0, i));
        binaryValue = binaryValue.substring(i);
        return popped;
    }

    public class BIterator implements Iterator<Boolean> {
        int offset = 0;

        @Override
        public boolean hasNext() {
            return offset < binaryValue.length();
        }

        @Override
        public Boolean next() {
            Boolean val = getBit(offset);
            offset++;
            return val;
        }
    }
}
