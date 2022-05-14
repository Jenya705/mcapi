package com.github.jenya705.mcapi.server.ss.model.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.jenya705.mcapi.server.module.inject.InjectJoining;
import com.github.jenya705.mcapi.server.util.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * @author Jenya705
 */
public class ProxyJsonModel implements InjectJoining {

    private final Collection<Pair<String, Object>> join;

    public ProxyJsonModel(JsonNode node) {
        join = new ArrayList<>(node.size());
        node.fields().forEachRemaining(stringJsonNodeEntry ->
                join.add(Pair.fromEntry(stringJsonNodeEntry))
        );
    }

    @Override
    public Collection<Pair<String, Object>> join() {
        return Collections.unmodifiableCollection(join);
    }
}
