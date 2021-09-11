package com.github.jenya705.mcapi.error;

import com.github.jenya705.mcapi.ApiError;

/**
 * @author Jenya705
 */
public class BotCommandNotExistException extends IllegalArgumentException implements ApiError {

    private static final int code = 12;
    private static final String format = "%s bot command not exist";

    public BotCommandNotExistException(String command) {
        super(String.format(format, command));
    }

    @Override
    public String getNamespace() {
        return ApiError.defaultNamespace;
    }

    @Override
    public String getReason() {
        return getMessage();
    }

    @Override
    public int getCode() {
        return code;
    }
}
