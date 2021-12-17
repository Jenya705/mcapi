package com.github.jenya705.mcapi.error;

import com.github.jenya705.mcapi.ApiError;

import java.util.UUID;

/**
 * @author Jenya705
 */
public class EntityNotCapturableException extends IllegalStateException implements ApiError {

    private static final int status = 403;
    private static final int code = 26;
    private static final String format = "Entity %s is not capturable";

    public EntityNotCapturableException(String message) {
        super(message);
    }

    public static EntityNotCapturableException create(UUID uuid) {
        return new EntityNotCapturableException(String.format(format, uuid));
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
