package com.github.jenya705.mcapi.server.module.selector;

import com.github.jenya705.mcapi.UUIDHolder;
import com.github.jenya705.mcapi.server.util.Selector;
import com.github.jenya705.mcapi.server.util.SelectorContainer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author Jenya705
 */
public class MapSelectorCreator<T, V> implements SelectorCreator<T, V> {

    private final Map<String, Function<V, Mono<Selector<T>>>> selectors = new HashMap<>();
    private BiFunction<V, String, Mono<Selector<T>>> direct;

    @Override
    public Mono<Selector<T>> create(String selector, V data) {
        String loweredSelector = selector.toLowerCase(Locale.ROOT);
        if (selectors.containsKey(loweredSelector)) {
            return selectors.get(loweredSelector).apply(data);
        }
        return direct.apply(data, selector);
    }

    public MapSelectorCreator<T, V> direct(BiFunction<V, String, Mono<Selector<T>>> selectorFunction) {
        this.direct = selectorFunction;
        return this;
    }

    public MapSelectorCreator<T, V> uuidDirect(BiFunction<V, String, Mono<T>> selectorFunction) {
        return direct((data, str) -> selectorFunction.apply(data, str)
                .flatMap(it -> it instanceof UUIDHolder ?
                        Mono.just(it) :
                        Mono.error(new IllegalArgumentException("Return object is not ApiUUID object"))
                )
                .map(it -> new SelectorContainer<>(
                        it,
                        "",
                        ((UUIDHolder) it).getUuid()
                ))
        );
    }

    public MapSelectorCreator<T, V> selector(String selector, Function<V, Mono<Selector<T>>> selectorFunction) {
        selectors.put("@" + selector.toLowerCase(Locale.ROOT), selectorFunction);
        return this;
    }

    public MapSelectorCreator<T, V> defaultSelector(String selector, Function<V, Flux<T>> selectorFunction) {
        return selector(
                selector,
                data -> selectorFunction.apply(data)
                        .collectList()
                        .map(selects -> new SelectorContainer<>(
                                selects, ".@" + selector, null
                        ))
        );
    }
}
