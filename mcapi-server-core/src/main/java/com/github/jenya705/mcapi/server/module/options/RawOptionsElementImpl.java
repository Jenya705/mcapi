package com.github.jenya705.mcapi.server.module.options;

import com.github.jenya705.mcapi.server.module.mapper.Mapper;
import lombok.RequiredArgsConstructor;

/**
 * @author Jenya705
 */
@RequiredArgsConstructor
public class RawOptionsElementImpl implements RawOptionsElement {

    private final String value;
    private final Mapper mapper;

    @Override
    public <T> T as(Class<? extends T> clazz) {
        return mapper.fromRaw(value, clazz);
    }

    @Override
    public String get() {
        return value;
    }
}
