package com.github.jenya705.mcapi.error;

import com.github.jenya705.mcapi.ApiError;

/**
 * @author Jenya705
 */
public class PlayerIdFormatException extends IllegalArgumentException implements ApiError {

    private static final int code = 2;
    private static final String format = "Player id %s is not right";

    public PlayerIdFormatException(String id) {
        super(String.format(format, id));
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
