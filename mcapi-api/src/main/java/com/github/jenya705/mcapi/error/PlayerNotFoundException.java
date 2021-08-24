package com.github.jenya705.mcapi.error;

import com.github.jenya705.mcapi.ApiError;

/**
 * @author Jenya705
 */
public class PlayerNotFoundException extends IllegalArgumentException implements ApiError {

    private static final int code = 1;
    private static final String format = "Player %s is not found";

    public PlayerNotFoundException(String name) {
        super(String.format(format, name));
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
