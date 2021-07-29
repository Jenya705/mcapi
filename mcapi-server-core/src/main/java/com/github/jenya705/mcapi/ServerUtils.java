package com.github.jenya705.mcapi;

import lombok.experimental.UtilityClass;

/**
 * @author Jenya705
 */
@UtilityClass
public class ServerUtils {

    public static String highFirstLetter(String str) {
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

}
