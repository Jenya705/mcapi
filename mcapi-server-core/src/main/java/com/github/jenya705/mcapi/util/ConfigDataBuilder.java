package com.github.jenya705.mcapi.util;

import com.github.jenya705.mcapi.data.ConfigData;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Consumer;

/**
 * @author Jenya705
 */
@Getter
@AllArgsConstructor
public class ConfigDataBuilder {

    private final ConfigData data;

    public ConfigDataBuilder put(String key, Object value) {
        data.set(key, value);
        return this;
    }

    public ConfigDataBuilder directory(String key, Consumer<ConfigDataBuilder> consumer) {
        ConfigData newData = data.required(key);
        consumer.accept(new ConfigDataBuilder(newData));
        return this;
    }

}
