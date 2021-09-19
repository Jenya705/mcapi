package com.github.jenya705.mcapi.error;

import com.github.jenya705.mcapi.ApiError;

/**
 * @author Jenya705
 */
public class LinkRequestPermissionNotFoundException extends IllegalArgumentException implements ApiError {

    private static final int code = 8;
    private static final String format = "Permission %s is not found";
    private static final int status = 404;

    public LinkRequestPermissionNotFoundException(String permissionName) {
        super(String.format(format, permissionName));
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
