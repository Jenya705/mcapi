package com.github.jenya705.mcapi.error;

import com.github.jenya705.mcapi.ApiError;

/**
 * @author Jenya705
 */
public class BlockDataNotFoundException extends IllegalArgumentException implements ApiError {

    private static final int code = 22;
    private static final int status = 404;

    public BlockDataNotFoundException(String message) {
        super(message);
    }

    public static BlockDataNotFoundException create() {
        return new BlockDataNotFoundException("Block data is not found");
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
