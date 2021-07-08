package com.github.jenya705.mcapi;

import lombok.experimental.UtilityClass;

/**
 * @author Jenya705
 */
@UtilityClass
public class JavaErrorUtils {

    public static ApiError playerNotFound(String name) {
        return new JavaApiError(String.format("Player %s is not found", name));
    }

}
