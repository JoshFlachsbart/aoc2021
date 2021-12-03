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
        return Integer.parseInt(binaryValue, 2);
    }

    public boolean getBit(int index) {
        boolean isSet = false;
        if (index < binaryValue.length()) {
            isSet = binaryValue.charAt(index) == '1';
        }
        return isSet;
    }

    public void appendBit(boolean bit) {
        binaryValue = binaryValue + (bit ? '1' : '0');
    }

    @Override
    public Iterator<Boolean> iterator() {
        return new BIterator();
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
