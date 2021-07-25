package com.github.jenya705.mcapi;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @since 1.0
 * @author Jenya705
 */
public class BukkitServerConfiguration implements JavaServerConfiguration {

    private final JavaPlugin plugin;

    public BukkitServerConfiguration(JavaPlugin plugin) {
        this.plugin = plugin;
        putIfNotExists("sql.user", "root");
        putIfNotExists("sql.host", "localhost:3306");
        putIfNotExists("sql.password", "1");
        putIfNotExists("sql.type", "mysql");
        putIfNotExists("sql.database", "minecraft");
        putIfNotExists("message.createTokenPlayerNameIsNotGiven", "&cPlayer name is not given");
        putIfNotExists("message.createTokenPlayerIsNotExist", "&cGiven player name is not belong to any player");
        putIfNotExists("message.createTokenNameIsNotGiven", "&cToken name is not given");
        putIfNotExists("message.createTokenSuccess", "&aSuccessfully registered token with name %name%:\n");
        putIfNotExists("message.subContainerCommandNotExist", "&eSub command %name% is not exist");
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

    @Override
    public String getCreateTokenPlayerNameIsNotGivenMessage() {
        return translateColorChat(plugin.getConfig().getString("message.createTokenPlayerNameIsNotGiven"));
    }

    @Override
    public String getCreateTokenPlayerIsNotExistMessage() {
        return translateColorChat(plugin.getConfig().getString("message.createTokenPlayerIsNotExist"));
    }

    @Override
    public String getCreateTokenNameIsNotGivenMessage() {
        return translateColorChat(plugin.getConfig().getString("message.createTokenNameIsNotGiven"));
    }

    @Override
    public String getCreateTokenSuccess() {
        return translateColorChat(plugin.getConfig().getString("message.createTokenSuccess"));
    }

    @Override
    public String getSubContainerCommandNotExist() {
        return translateColorChat(plugin.getConfig().getString("message.subContainerCommandNotExist"));
    }

    protected String translateColorChat(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    protected void putIfNotExists(String key, Object value) {
        if (!plugin.getConfig().contains(key)) plugin.getConfig().set(key, value);
    }
}
