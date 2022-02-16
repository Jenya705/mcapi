package com.github.jenya705.mcapi.server.module.options;

/**
 * @author Jenya705
 */
public interface RawOptionsElement {

    <T> T as(Class<? extends T> clazz);

    String get();

}
