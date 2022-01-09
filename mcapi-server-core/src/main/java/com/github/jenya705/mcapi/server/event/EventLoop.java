package com.github.jenya705.mcapi.server.event;

import java.util.function.Consumer;

/**
 * @author Jenya705
 */
public interface EventLoop {

    <T> EventLoop handler(Class<? extends T> clazz, Consumer<T> handler);

    <T> EventLoop asyncHandler(Class<? extends T> clazz, Consumer<T> handler);

    void invoke(Object event);

}
