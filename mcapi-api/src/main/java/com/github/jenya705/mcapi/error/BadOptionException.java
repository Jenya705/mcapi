package com.github.jenya705.mcapi.error;

import com.github.jenya705.mcapi.ApiError;

/**
 * @author Jenya705
 */
public class BadOptionException extends IllegalArgumentException implements ApiError {

    private static final int status = 403;
    private static final int code = 18;
    private static final String format = "Bad option with name %s";

    public BadOptionException(String message) {
        super(message);
    }

    public static BadOptionException create(String option) {
        return new BadOptionException(String.format(format, option));
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
    public String getReason() {
        return getMessage();
    }

    @Override
    public String getNamespace() {
        return ApiError.defaultNamespace;
    }
}
