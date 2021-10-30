package com.github.jenya705.mcapi;

import java.util.function.Consumer;

/**
 * @author Jenya705
 */
public interface TunnelClient {

    <T> void on(Class<? extends T> clazz, Consumer<T> handler);

    void addEventType(Class<?> clazz, String type);

    void disable();

}
