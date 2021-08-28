package com.github.jenya705.mcapi.data;

import java.util.Optional;

/**
 * @author Jenya705
 */
public interface GlobalContainer {

    String inheritKey = "__inherit__";

    GlobalContainer global(String key, Object value);

    Optional<Object> global(String key);

    Object requiredGlobal(String key, Object value);

}
