package com.github.jenya705.mcapi.error;

import com.github.jenya705.mcapi.ApiError;

/**
 * @author Jenya705
 */
public class CommandNameFormatException extends IllegalArgumentException implements ApiError {

    private static final int code = 11;
    private static final String format = "Command name or option name is not matches %s pattern";
    private static final int status = 400;

    public CommandNameFormatException(String message) {
        super(message);
    }

    public static CommandNameFormatException create(String pattern) {
        return new CommandNameFormatException(String.format(format, pattern));
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
