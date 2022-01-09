package com.github.jenya705.mcapi.server.module.selector;

import com.github.jenya705.mcapi.server.util.Selector;

/**
 * @author Jenya705
 */
public interface SelectorCreator<T, V> {

    Selector<T> create(String selector, V data);
}
