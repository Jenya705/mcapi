package dev.mcapi.config;

import dev.mcapi.config.reader.ConfigImpl;
import dev.mcapi.config.reader.YamlConfigImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ConfigType {

    YAML("yml", new YamlConfigImpl())
    ;

    private final String format;
    private final ConfigImpl impl;

}
