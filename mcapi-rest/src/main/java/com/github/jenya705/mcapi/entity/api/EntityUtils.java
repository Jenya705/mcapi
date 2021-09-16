package com.github.jenya705.mcapi.entity.api;

import lombok.experimental.UtilityClass;

/**
 * @author Jenya705
 */
@UtilityClass
public class EntityUtils {

    public void throwEntityContextException() {
        throw new UnsupportedOperationException("Can not be invoked because of entity");
    }

}
