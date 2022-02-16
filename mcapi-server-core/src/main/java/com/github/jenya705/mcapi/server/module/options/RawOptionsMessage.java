package com.github.jenya705.mcapi.server.module.options;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
public interface RawOptionsMessage {

    List<RawOptionsElement> getOptions(String name);

    default <T> List<? extends T> getOptions(String name, Class<? extends T> clazz) {
        return getOptions(name)
                .stream()
                .map(it -> it.as(clazz))
                .collect(Collectors.toList());
    }

}
