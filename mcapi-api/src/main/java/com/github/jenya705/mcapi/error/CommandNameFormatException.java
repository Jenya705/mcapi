package com.github.jenya705.mcapi.error;

import com.github.jenya705.mcapi.ApiError;

/**
 * @author Jenya705
 */
public class CommandNameFormatException extends IllegalArgumentException implements ApiError {

    public static final int code = 11;
    public static final String format = "Command name or option name is not matches %s pattern";

    public CommandNameFormatException(String pattern) {
        super(String.format(format, pattern));
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
