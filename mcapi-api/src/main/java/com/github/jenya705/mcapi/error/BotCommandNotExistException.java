package com.github.jenya705.mcapi.error;

import com.github.jenya705.mcapi.ApiError;

/**
 * @author Jenya705
 */
public class BotCommandNotExistException extends IllegalArgumentException implements ApiError {

    private static final int code = 12;
    private static final String format = "%s bot command not exist";
    private static final int status = 404;

    public BotCommandNotExistException(String message) {
        super(message);
    }

    public static BotCommandNotExistException create(String command) {
        return new BotCommandNotExistException(String.format(format, command));
    }

    @Override
    public int getStatusCode() {
        return status;
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
