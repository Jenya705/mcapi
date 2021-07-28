package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.data.ServerData;
import lombok.AllArgsConstructor;
import org.bukkit.configuration.Configuration;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class BukkitServerData implements ServerData {

    private final Configuration configuration;

    @Override
    public String getString(String key) {
        return configuration.getString(key);
    }

    @Override
    public Integer getInteger(String key) {
        return configuration.getInt(key);
    }

    @Override
    public boolean getBoolean(String key) {
        return configuration.getBoolean(key);
    }

    @Override
    public void put(String key, Object obj, boolean putIfExists) {
        if (putIfExists && configuration.contains(key)) configuration.set(key, obj);
    }
}
