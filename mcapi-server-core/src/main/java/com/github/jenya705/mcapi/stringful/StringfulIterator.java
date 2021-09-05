package com.github.jenya705.mcapi.stringful;

import java.util.Iterator;

/**
 * @author Jenya705
 */
public interface StringfulIterator extends Iterator<String> {

    boolean hasNext(int count);

    int countNext();

    void back();

    default String[] allNext() {
        String[] values = new String[countNext()];
        int i = 0;
        while (hasNext()) values[i++] = next();
        return values;
    }

}
