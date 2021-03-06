package com.github.jenya705.mcapi.server.stringful;

/**
 * @author Jenya705
 */
@FunctionalInterface
public interface StringfulDataValueFunction<T> {
    void accept(T data, String value) throws Exception;
}
