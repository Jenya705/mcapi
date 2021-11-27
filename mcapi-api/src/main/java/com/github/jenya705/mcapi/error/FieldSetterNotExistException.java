package com.github.jenya705.mcapi.error;

import com.github.jenya705.mcapi.ApiError;

/**
 * @author Jenya705
 */
public class FieldSetterNotExistException extends IllegalArgumentException implements ApiError {

    private static final int code = 24;
    private static final int status = 404;

    public FieldSetterNotExistException(String message) {
        super(message);
    }

    public static FieldSetterNotExistException create(String fieldName) {
        return new FieldSetterNotExistException(String.format("Field with name %s is not exist", fieldName));
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
