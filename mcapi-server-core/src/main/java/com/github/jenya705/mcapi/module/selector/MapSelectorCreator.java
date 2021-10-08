package com.github.jenya705.mcapi.module.selector;

import com.github.jenya705.mcapi.UUIDHolder;
import com.github.jenya705.mcapi.util.Selector;
import com.github.jenya705.mcapi.util.SelectorContainer;

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

    private final Map<String, Function<V, Selector<T>>> selectors = new HashMap<>();
    private BiFunction<V, String, Selector<T>> direct;

    @Override
    public Selector<T> create(String selector, V data) {
        String loweredSelector = selector.toLowerCase(Locale.ROOT);
        if (selectors.containsKey(loweredSelector)) {
            return selectors.get(loweredSelector).apply(data);
        }
        return direct.apply(data, selector);
    }

    public MapSelectorCreator<T, V> direct(BiFunction<V, String, Selector<T>> selectorFunction) {
        this.direct = selectorFunction;
        return this;
    }

    public MapSelectorCreator<T, V> uuidDirect(BiFunction<V, String, T> selectorFunction) {
        return direct((data, str) -> {
            T uuidObject = selectorFunction.apply(data, str);
            if (!(uuidObject instanceof UUIDHolder)) {
                throw new IllegalArgumentException("Return object is not ApiUUID object");
            }
            return new SelectorContainer<>(
                    uuidObject,
                    "",
                    ((UUIDHolder) uuidObject).getUuid()
            );
        });
    }

    public MapSelectorCreator<T, V> selector(String selector, Function<V, Selector<T>> selectorFunction) {
        selectors.put("@" + selector.toLowerCase(Locale.ROOT), selectorFunction);
        return this;
    }

    public MapSelectorCreator<T, V> defaultSelector(String selector, Function<V, Collection<T>> selectorFunction) {
        return selector(
                selector,
                data -> new SelectorContainer<>(
                        selectorFunction.apply(data),
                        ".@" + selector,
                        null
                )
        );
    }
}
