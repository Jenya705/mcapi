package com.github.jenya705.mcapi.module.rest;

import java.io.IOException;

/**
 * @author Jenya705
 */
public interface ObjectTunnelFunction<T, E> {

    E tunnel(T obj) throws IOException;
}
