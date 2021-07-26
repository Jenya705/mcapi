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
        putIfNotExists("message.tokenPlayerNameIsNotGiven", "&cPlayer name is not given");
        putIfNotExists("message.tokenPlayerIsNotExist", "&cGiven player name is not belong to any player");
        putIfNotExists("message.createTokenNameIsNotGiven", "&cToken name is not given");
        putIfNotExists("message.createTokenSuccess", "&aSuccessfully registered token with name %name%:\n");
        putIfNotExists("message.subContainerCommandNotExist", "&eSub command %name% is not exist");
        putIfNotExists("message.subContainerHelpLayout", "&ePossible variants:\n %commands%");
        putIfNotExists("message.subContainerHelpDelimiter", ", ");
        putIfNotExists("message.listTokenLayout", "&b%name% : &e");
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
    public String getTokenPlayerNameIsNotGivenMessage() {
        return translateColorChat(plugin.getConfig().getString("message.tokenPlayerNameIsNotGiven"));
    }

    @Override
    public String getTokenPlayerIsNotExistMessage() {
        return translateColorChat(plugin.getConfig().getString("message.tokenPlayerIsNotExist"));
    }

    @Override
    public String getCreateTokenNameIsNotGivenMessage() {
        return translateColorChat(plugin.getConfig().getString("message.createTokenNameIsNotGiven"));
    }

    @Override
    public String getCreateTokenSuccessMessage() {
        return translateColorChat(plugin.getConfig().getString("message.createTokenSuccess"));
    }

    @Override
    public String getSubContainerCommandNotExistMessage() {
        return translateColorChat(plugin.getConfig().getString("message.subContainerCommandNotExist"));
    }

    @Override
    public String getSubContainerHelpLayout() {
        return translateColorChat(plugin.getConfig().getString("message.subContainerHelpLayout"));
    }

    @Override
    public String getSubContainerHelpDelimiter() {
        return translateColorChat(plugin.getConfig().getString("message.subContainerHelpDelimiter"));
    }

    @Override
    public String getListTokenLayout() {
        return translateColorChat(plugin.getConfig().getString("message.listTokenLayout"));
    }

    protected String translateColorChat(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    protected void putIfNotExists(String key, Object value) {
        if (!plugin.getConfig().contains(key)) plugin.getConfig().set(key, value);
    }
}
