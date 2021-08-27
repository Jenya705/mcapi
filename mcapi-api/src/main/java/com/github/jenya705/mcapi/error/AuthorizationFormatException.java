package com.github.jenya705.mcapi.error;

import com.github.jenya705.mcapi.ApiError;

/**
 * @author Jenya705
 */
public class AuthorizationFormatException extends IllegalArgumentException implements ApiError {

    private static final int code = 3;

    public AuthorizationFormatException() {
        super("Bad format");
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