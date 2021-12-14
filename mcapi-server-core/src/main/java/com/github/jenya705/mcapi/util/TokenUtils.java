package com.github.jenya705.mcapi.util;

import lombok.experimental.UtilityClass;

import java.util.Date;
import java.util.UUID;

/**
 * @author Jenya705
 */
@UtilityClass
public class TokenUtils {

    public String generateToken() {
        return UUID.randomUUID().toString().replace("-", "") +
                String.format("%019d", new Date().getTime());
    }
}
