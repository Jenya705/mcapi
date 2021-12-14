package com.github.jenya705.mcapi.error;

import com.github.jenya705.mcapi.ApiError;

import java.util.UUID;

/**
 * @author Jenya705
 */
public class EntityNotFoundException extends IllegalArgumentException implements ApiError {

    private static final int status = 404;
    private static final int code = 25;
    private static final String format = "Entity with uuid %s is not found";

    public EntityNotFoundException(String message) {
        super(message);
    }

    public static EntityNotFoundException create(UUID uuid) {
        return new EntityNotFoundException(String.format(format, uuid));
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
