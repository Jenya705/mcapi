package com.github.jenya705.mcapi;

import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @since 1.0
 * @author Jenya705
 */
public class BukkitServerConfiguration implements ApiServerConfiguration {

    private final JavaPlugin plugin;

    public BukkitServerConfiguration(JavaPlugin plugin) {
        this.plugin = plugin;
        putIfNotExists("sql.user", "root");
        putIfNotExists("sql.host", "localhost:3306");
        putIfNotExists("sql.password", "1");
        putIfNotExists("sql.type", "mysql");
        putIfNotExists("sql.database", "minecraft");
    }

    @Override
    public String getSqlUser() {
        return plugin.getConfig().getString("sql.user");
    }

    @Override
    public String getSqlHost() {
        return plugin.getConfig().getString("sql.host");
    }

    @Override
    public String getSqlPassword() {
        return plugin.getConfig().getString("sql.password");
    }

    @Override
    public String getSqlType() {
        return plugin.getConfig().getString("sql.type");
    }

    @Override
    public String getSqlDatabase() {
        return plugin.getConfig().getString("sql.database");
    }

    protected void putIfNotExists(String key, Object value) {
        if (!plugin.getConfig().contains(key)) plugin.getConfig().set(key, value);
    }
}
