package com.github.jenya705.mcapi.error;

import com.github.jenya705.mcapi.ApiError;

/**
 * @author Jenya705
 */
public class AuthorizationBadTokenException extends IllegalArgumentException implements ApiError {

    private static final int code = 4;
    private static final int status = 401;

    public AuthorizationBadTokenException(String message) {
        super(message);
    }

    public static AuthorizationBadTokenException create() {
        return new AuthorizationBadTokenException("Bad token");
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
