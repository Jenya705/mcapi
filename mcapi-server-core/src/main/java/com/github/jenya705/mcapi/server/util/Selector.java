package com.github.jenya705.mcapi.server.util;

import reactor.core.publisher.Flux;

import java.util.Collection;
import java.util.UUID;

/**
 * @author Jenya705
 */
public interface Selector<T> {

    Collection<T> all();

    String getPermissionName();

    UUID getTarget();
}
