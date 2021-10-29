package com.github.jenya705.mcapi.error;

import com.github.jenya705.mcapi.ApiError;

/**
 * @author Jenya705
 */
public class AuthorizationFormatException extends IllegalArgumentException implements ApiError {

    private static final int code = 3;
    private static final int status = 401;

    public AuthorizationFormatException(String message) {
        super(message);
    }

    public static AuthorizationFormatException create() {
        return new AuthorizationFormatException("Bad format");
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
