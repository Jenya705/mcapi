package com.github.jenya705.mcapi.util;

import lombok.experimental.UtilityClass;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * @author Jenya705
 */
@UtilityClass
public class TokenUtils {

    private final Random secureRandom = new SecureRandom();

    public String generateToken() {
        return new UUID(secureRandom.nextLong(), secureRandom.nextLong()).toString().replace("-", "") +
                String.format("%019d", new Date().getTime());
    }
}
