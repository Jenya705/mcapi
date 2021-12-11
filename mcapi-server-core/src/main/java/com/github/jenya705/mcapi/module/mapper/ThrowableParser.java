package com.github.jenya705.mcapi.module.mapper;

import com.github.jenya705.mcapi.ApiError;

/**
 * @author Jenya705
 */
@FunctionalInterface
public interface ThrowableParser {

    ApiError parse(Throwable throwable);
}
