package com.github.jenya705.mcapi.server.module.selector;

import com.github.jenya705.mcapi.server.util.Selector;
import reactor.core.publisher.Mono;

/**
 * @author Jenya705
 */
public interface SelectorCreator<T, V> {

    Mono<Selector<T>> create(String selector, V data);
}
