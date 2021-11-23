package com.github.jenya705.mcapi.error;

import com.github.jenya705.mcapi.ApiError;

/**
 * @author Jenya705
 */
public class LinkExistException extends IllegalStateException implements ApiError {

    public static final int code = 23;
    public static final int status = 403;

    public LinkExistException(String message) {
        super(message);
    }

    public static LinkExistException create() {
        return new LinkExistException("You are already linked with this player");
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
