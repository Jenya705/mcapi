package com.github.jenya705.mcapi.data;

/**
 * @author Jenya705
 */
public interface ServerData {

    String getString(String key);

    Integer getInteger(String key);

    boolean getBoolean(String key);

    void put(String key, Object obj, boolean putIfExists);

}
