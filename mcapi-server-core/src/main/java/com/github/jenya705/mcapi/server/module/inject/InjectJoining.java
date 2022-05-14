package com.github.jenya705.mcapi.server.module.inject;

import com.github.jenya705.mcapi.server.util.Pair;

import java.util.Collection;

/**
 * @author Jenya705
 */
@FunctionalInterface
public interface InjectJoining {

    Collection<Pair<String, Object>> join();

}
