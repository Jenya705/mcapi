package com.github.jenya705.mcapi.error;

import com.github.jenya705.mcapi.ApiError;

/**
 * @author Jenya705
 */
public class MessageTypeNotExistException extends IllegalArgumentException implements ApiError {

    private static final int status = 400;
    private static final int code = 15;
    private static final String format = "Message type %s is not exist";

    public MessageTypeNotExistException(String type) {
        super(String.format(format, type));
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
