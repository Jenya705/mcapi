package com.github.jenya705.mcapi.exception;

import java.util.Map;

/**
 *
 * An api exception represents rest object of exception
 *
 * @since 1.0
 * @author Jenya705
 */
public interface ApiException {

    String getNamespace();

    int getCode();

    String getMessage();

    default Map<String, Object> map() {
        return Map.of(
                "namespace", getNamespace(),
                "code", getCode(),
                "message", getMessage()
        );
    }

    static Map<String, Object> map(Throwable throwable) {
        if (throwable instanceof ApiException) return ((ApiException) throwable).map();
        return Map.of(
                "namespace", throwable.getClass().getCanonicalName(),
                "code", 0,
                "message", throwable.getMessage()
        );
    }

}
