package com.github.jenya705.mcapi.error;

import com.github.jenya705.mcapi.ApiError;

/**
 * @author Jenya705
 */
public class TooManyOptionsException extends IllegalArgumentException implements ApiError {

    private static final int status = 400;
    private static final int code = 19;
    private static final String format = "Too many options, max: %s";

    public TooManyOptionsException(String message) {
        super(message);
    }

    public static TooManyOptionsException create(int max) {
        return new TooManyOptionsException(String.format(format, max));
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
