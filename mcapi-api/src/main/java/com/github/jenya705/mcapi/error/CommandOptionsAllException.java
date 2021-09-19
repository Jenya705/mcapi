package com.github.jenya705.mcapi.error;

import com.github.jenya705.mcapi.ApiError;

/**
 * @author Jenya705
 */
public class CommandOptionsAllException extends IllegalArgumentException implements ApiError {

    private static final int code = 10;
    private static final int status = 400;

    public CommandOptionsAllException() {
        super("Options of command is combined (please insert sub commands or values)");
    }

    @Override
    public int getStatusCode() {
        return status;
    }

    @Override
    public String getReason() {
        return getMessage();
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getNamespace() {
        return ApiError.defaultNamespace;
    }
}
