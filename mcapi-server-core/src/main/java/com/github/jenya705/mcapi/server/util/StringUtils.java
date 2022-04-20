package com.github.jenya705.mcapi.server.util;

import lombok.experimental.UtilityClass;

import java.nio.charset.StandardCharsets;

/**
 * @author Jenya705
 */
@UtilityClass
public class StringUtils {

    public byte[] getStandardBytes(String str) {
        return str.getBytes(StandardCharsets.UTF_8);
    }

}
