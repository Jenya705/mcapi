package com.github.jenya705.mcapi.server.util;

/**
 * @author Jenya705
 */
@FunctionalInterface
public interface ExceptionableFunction<T, V> {

    V accept(T obj) throws Exception;
}
