package com.github.jenya705.mcapi.stringful;

/**
 * @author Jenya705
 */
public class ArrayStringfulIterator implements StringfulIterator {

    private final String[] array;
    private int current;

    public ArrayStringfulIterator(String[] array) {
        this.array = array;
    }

    @Override
    public boolean hasNext(int count) {
        return current + count - 1 < array.length;
    }

    @Override
    public int countNext() {
        return array.length - current;
    }

    @Override
    public boolean hasNext() {
        return current < array.length;
    }

    @Override
    public void back() {
        if (current > 0) current--;
    }

    @Override
    public String next() {
        if (!hasNext()) {
            throw new IllegalStateException("Next argument is not exist");
        }
        return array[current++];
    }
}
