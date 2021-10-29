package com.github.jenya705.mcapi.error;

import com.github.jenya705.mcapi.ApiError;

/**
 * @author Jenya705
 */
public class PlayerIdFormatException extends IllegalArgumentException implements ApiError {

    private static final int code = 2;
    private static final String format = "Player id %s is not right";
    private static final int status = 403;

    public PlayerIdFormatException(String message) {
        super(message);
    }

    public static PlayerIdFormatException create(String id) {
        return new PlayerIdFormatException(String.format(format, id));
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
