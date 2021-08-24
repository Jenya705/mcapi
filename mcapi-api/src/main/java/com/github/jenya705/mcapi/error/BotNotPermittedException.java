package com.github.jenya705.mcapi.error;

import com.github.jenya705.mcapi.ApiError;

/**
 * @author Jenya705
 */
public class BotNotPermittedException extends IllegalStateException implements ApiError {

    private static final int code = 5;
    private static final String format = "You do not permitted to do that, permission: %s";

    public BotNotPermittedException(String permission) {
        super(String.format(format, permission));
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
