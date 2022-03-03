package com.github.jenya705.mcapi.server.module.database;

import com.github.jenya705.mcapi.server.module.database.storage.DatabaseStorage;

import java.sql.Connection;

/**
 * @author Jenya705
 */
public interface DatabaseTypeInitializer {

    Connection connection(String host, String user, String password, String database);

    default DatabaseStorage storage() {
        return null;
    }

}
