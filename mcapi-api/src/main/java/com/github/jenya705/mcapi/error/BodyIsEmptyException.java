package com.github.jenya705.mcapi.error;

import com.github.jenya705.mcapi.ApiError;

/**
 * @author Jenya705
 */
public class BodyIsEmptyException extends IllegalArgumentException implements ApiError {

    private static final int code = 13;
    private static final int status = 400;

    public BodyIsEmptyException(String message) {
        super(message);
    }

    public static BodyIsEmptyException create() {
        return new BodyIsEmptyException("Body is empty");
    }

    @Override
    public String getNamespace() {
        return ApiError.defaultNamespace;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public int getStatusCode() {
        return status;
    }

    @Override
    public String getReason() {
        return getMessage();
    }
}
