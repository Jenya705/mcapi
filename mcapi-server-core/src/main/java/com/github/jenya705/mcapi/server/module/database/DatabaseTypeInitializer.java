package com.github.jenya705.mcapi.server.module.database;

import com.github.jenya705.mcapi.server.module.database.sql.SQLConnectionManager;
import com.github.jenya705.mcapi.server.module.database.storage.DatabaseStorage;

/**
 * @author Jenya705
 */
public interface DatabaseTypeInitializer {

    SQLConnectionManager connection(String host, String user, String password, String database);

    default DatabaseStorage storage() {
        return null;
    }

}
