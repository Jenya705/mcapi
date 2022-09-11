package dev.mcapi.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Map;

public interface ConfigData {

    <T> T get(@NotNull String key);

    @Unmodifiable Map<String, Object> asMap();

}
