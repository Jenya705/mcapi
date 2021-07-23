package com.github.jenya705.mcapi.exception;

/**
 * @since 1.0
 * @author Jenya705
 */
public class ApiPermissionDeniedException extends IllegalStateException implements ApiException {

    public static final int code = 2;

    public ApiPermissionDeniedException(String permission) {
        super(String.format("You do not have permission %s", permission));
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getNamespace() {
        return "mcapi";
    }
}
