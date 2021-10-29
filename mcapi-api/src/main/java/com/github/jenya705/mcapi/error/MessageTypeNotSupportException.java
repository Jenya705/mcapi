package com.github.jenya705.mcapi.error;

import com.github.jenya705.mcapi.ApiError;

/**
 * @author Jenya705
 */
public class MessageTypeNotSupportException extends IllegalArgumentException implements ApiError {

    private static final int status = 400;
    private static final int code = 16;
    private static final String format = "Message type %s is not supported for this operation";

    public MessageTypeNotSupportException(String message) {
        super(message);
    }

    public static MessageTypeNotSupportException create(String type) {
        return new MessageTypeNotSupportException(String.format(format, type));
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
