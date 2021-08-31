package com.github.jenya705.mcapi.error;

import com.github.jenya705.mcapi.ApiError;

/**
 * @author Jenya705
 */
public class LinkRequestPermissionIsGlobalException extends IllegalArgumentException implements ApiError {

    private static final int code = 9;
    private static final String format = "Permission %s is global";

    public LinkRequestPermissionIsGlobalException(String permissionName) {
        super(String.format(format, permissionName));
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
