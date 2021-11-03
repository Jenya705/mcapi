package com.github.jenya705.mcapi.module.database;

import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.data.loadable.Value;
import com.github.jenya705.mcapi.module.config.Config;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jenya705
 */
@Getter
@Setter
@ToString
public class DatabaseModuleConfig extends Config {

    @Value
    private String type = "sqlite";
    @Value
    private String host = "localhost:3306";
    @Value
    private String user = "root";
    @Value
    private String password = "1";
    @Value
    private String database = "minecraft";

    public DatabaseModuleConfig(ConfigData config) {
        load(config);
    }
}
