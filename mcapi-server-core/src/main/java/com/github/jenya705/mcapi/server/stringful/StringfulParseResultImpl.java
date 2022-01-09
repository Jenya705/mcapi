package com.github.jenya705.mcapi.server.stringful;

import lombok.AllArgsConstructor;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class StringfulParseResultImpl<T> implements StringfulParseResult<T> {

    private final T result;
    private final StringfulParseError error;

    @Override
    public boolean isPresent() {
        return result != null;
    }

    @Override
    public T get() {
        if (isFailed()) throw new IllegalStateException("Result is not present");
        return result;
    }

    @Override
    public StringfulParseError error() {
        if (isPresent()) throw new IllegalStateException("Result is present");
        return error;
    }
}
