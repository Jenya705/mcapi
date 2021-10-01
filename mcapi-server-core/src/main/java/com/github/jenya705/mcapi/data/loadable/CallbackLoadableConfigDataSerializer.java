package com.github.jenya705.mcapi.data.loadable;

/**
 * @author Jenya705
 */
public interface CallbackLoadableConfigDataSerializer {

    Object serialize(Object obj);

    Object deserialize(Object obj, Class<?> type);

}
