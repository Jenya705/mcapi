package com.github.jenya705.mcapi.error;

import com.github.jenya705.mcapi.ApiError;

/**
 * @author Jenya705
 */
public class PlayerNotFoundException extends IllegalArgumentException implements ApiError {

    private static final int code = 1;
    private static final String format = "Player %s is not found";
    private static final int status = 404;

    public PlayerNotFoundException(String message) {
        super(message);
    }

    public static PlayerNotFoundException create(String id) {
        return new PlayerNotFoundException(String.format(format, id));
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
