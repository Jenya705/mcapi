package com.github.jenya705.mcapi.exception;

/**
 *
 * @since 1.0
 * @author Jenya705
 */
public class ApiPlayerNotFoundException extends IllegalArgumentException implements ApiException {

    public static final int code = 1;

    public ApiPlayerNotFoundException(String id) {
        super(String.format("%s player can not be found", id));
    }

    @Override
    public int getCode() {
        return 1;
    }

    @Override
    public String getNamespace() {
        return "mcapi";
    }
}
