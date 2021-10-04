package com.github.jenya705.mcapi;

import lombok.experimental.UtilityClass;

/**
 * @author Jenya705
 */
@UtilityClass
public class JavaPlatformUtils {

    public RuntimeException notValidObject() {
        return new IllegalArgumentException("This object is not valid java platform object");
    }

}
