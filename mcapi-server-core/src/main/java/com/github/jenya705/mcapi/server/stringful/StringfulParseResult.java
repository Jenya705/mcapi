package com.github.jenya705.mcapi.server.stringful;

import java.util.function.Consumer;

/**
 * @author Jenya705
 */
public interface StringfulParseResult<T> {

    boolean isPresent();

    default boolean isFailed() {
        return !isPresent();
    }

    T get();

    StringfulParseError error();

    default StringfulParseResult<T> ifPresent(Consumer<T> function) {
        if (isPresent()) function.accept(get());
        return this;
    }

    default StringfulParseResult<T> ifFailed(Consumer<StringfulParseError> function) {
        if (isFailed()) function.accept(error());
        return this;
    }
}
