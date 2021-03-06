package com.github.jenya705.mcapi.server.data;

import java.util.Optional;

/**
 * @author Jenya705
 */
public interface GlobalContainer {

    String inheritKey = "__inherit__";

    String globalKey = "__globals__";

    GlobalContainer global(String key, Object value);

    Optional<Object> global(String key);

    Object requiredGlobal(String key, Object value);
}
