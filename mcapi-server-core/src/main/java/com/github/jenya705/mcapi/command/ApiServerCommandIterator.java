package com.github.jenya705.mcapi.command;

import java.util.Iterator;

/**
 * @since 1.0
 * @author Jenya705
 */
public interface ApiServerCommandIterator<T> extends Iterator<T> {

    boolean hasNext(int count);

    T back();

}
