package com.github.jenya705.mcapi.error;

import com.github.jenya705.mcapi.ApiError;

/**
 * @author Jenya705
 */
public class JsonDeserializeException extends IllegalArgumentException implements ApiError {

    private static final int code = 14;
    private static final int status = 400;

    public JsonDeserializeException() {
        super("Can not deserialize given json");
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
