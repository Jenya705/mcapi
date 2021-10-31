package com.github.jenya705.mcapi.error;

import com.github.jenya705.mcapi.ApiError;

/**
 * @author Jenya705
 */
public class BlockNotFoundException extends IllegalArgumentException implements ApiError {

    private static final int status = 404;
    private static final int code = 21;

    public BlockNotFoundException(String message) {
        super(message);
    }

    public static BlockNotFoundException create() {
        return new BlockNotFoundException("Block not found");
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
