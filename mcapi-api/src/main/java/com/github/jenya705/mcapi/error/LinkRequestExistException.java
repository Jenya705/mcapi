package com.github.jenya705.mcapi.error;

import com.github.jenya705.mcapi.ApiError;

/**
 * @author Jenya705
 */
public class LinkRequestExistException extends IllegalStateException implements ApiError {

    private static final int code = 7;
    private static final int status = 403;

    public LinkRequestExistException(String message) {
        super(message);
    }

    public static LinkRequestExistException create() {
        return new LinkRequestExistException("Link request already exist");
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
    public String getReason() {
        return getMessage();
    }

    @Override
    public String getNamespace() {
        return ApiError.defaultNamespace;
    }
}
