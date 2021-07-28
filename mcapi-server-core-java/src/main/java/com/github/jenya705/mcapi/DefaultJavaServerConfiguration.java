package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.data.ServerData;

/**
 * @author Jenya705
 */
public class DefaultJavaServerConfiguration implements JavaServerConfiguration {

    private final ServerData serverData;

    public DefaultJavaServerConfiguration(ServerData serverData) {
        this.serverData = serverData;
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
        return serverData.getString("sql.user");
    }

    @Override
    public String getSqlHost() {
        return serverData.getString("sql.host");
    }

    @Override
    public String getSqlPassword() {
        return serverData.getString("sql.password");
    }

    @Override
    public String getSqlType() {
        return serverData.getString("sql.type");
    }

    @Override
    public String getSqlDatabase() {
        return serverData.getString("sql.database");
    }

    @Override
    public String getTokenPlayerNameIsNotGivenMessage() {
        return translateColorChat(serverData.getString("message.tokenPlayerNameIsNotGiven"));
    }

    @Override
    public String getTokenPlayerIsNotExistMessage() {
        return translateColorChat(serverData.getString("message.tokenPlayerIsNotExist"));
    }

    @Override
    public String getCreateTokenNameIsNotGivenMessage() {
        return translateColorChat(serverData.getString("message.createTokenNameIsNotGiven"));
    }

    @Override
    public String getCreateTokenSuccessMessage() {
        return translateColorChat(serverData.getString("message.createTokenSuccess"));
    }

    @Override
    public String getSubContainerCommandNotExistMessage() {
        return translateColorChat(serverData.getString("message.subContainerCommandNotExist"));
    }

    @Override
    public String getSubContainerHelpLayout() {
        return translateColorChat(serverData.getString("message.subContainerHelpLayout"));
    }

    @Override
    public String getSubContainerHelpDelimiter() {
        return translateColorChat(serverData.getString("message.subContainerHelpDelimiter"));
    }

    @Override
    public String getListTokenLayout() {
        return translateColorChat(serverData.getString("message.listTokenLayout"));
    }

    protected String translateColorChat(String message) {
        return message.replaceAll("\u00A7", "&");
    }

    protected void putIfNotExists(String key, Object value) {
        serverData.put(key, value, false);
    }

}
