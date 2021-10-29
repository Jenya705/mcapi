package com.github.jenya705.mcapi.error;

import com.github.jenya705.mcapi.ApiError;

/**
 * @author Jenya705
 */
public class SelectorEmptyException extends IllegalArgumentException implements ApiError {

    private static final int status = 404;
    private static final int code = 17;

    public SelectorEmptyException(String message) {
        super(message);
    }

    public static SelectorEmptyException create() {
        return new SelectorEmptyException("Selector is empty");
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
