package com.github.jenya705.mcapi.error;

import com.github.jenya705.mcapi.ApiError;

/**
 * @author Jenya705
 */
public class BadUuidFormatException extends IllegalArgumentException implements ApiError {

    private static final int code = 6;
    private static final String format = "Bad uuid %s";

    public BadUuidFormatException(String uuid) {
        super(String.format(format, uuid));
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
