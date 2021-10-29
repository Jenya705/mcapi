package com.github.jenya705.mcapi.error;

import com.github.jenya705.mcapi.ApiError;

/**
 * @author Jenya705
 */
public class LinkRequestPermissionIsGlobalException extends IllegalArgumentException implements ApiError {

    private static final int code = 9;
    private static final String format = "Permission %s is global";
    private static final int status = 400;

    public LinkRequestPermissionIsGlobalException(String message) {
        super(message);
    }

    public static LinkRequestPermissionIsGlobalException create(String permissionName) {
        return new LinkRequestPermissionIsGlobalException(String.format(format, permissionName));
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
