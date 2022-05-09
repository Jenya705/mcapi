package com.github.jenya705.mcapi.error;

import com.github.jenya705.mcapi.ApiError;
import com.github.jenya705.mcapi.NamespacedKey;

/**
 * @author Jenya705
 */
public class WorldNotFoundException extends IllegalArgumentException implements ApiError {

    private static final int status = 404;
    private static final int code = 20;
    private static final String format = "World %s is not found";

    public WorldNotFoundException(String message) {
        super(message);
    }

    public static WorldNotFoundException create(String world) {
        return new WorldNotFoundException(String.format(format, world));
    }

    public static WorldNotFoundException create(NamespacedKey id) {
        return create(id.toString());
    }

    @Override
    public int getStatusCode() {
        return status;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getNamespace() {
        return ApiError.defaultNamespace;
    }

    @Override
    public String getReason() {
        return getMessage();
    }
}
