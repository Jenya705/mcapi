package com.github.jenya705.mcapi.server.module.database.sql;

import com.github.jenya705.mcapi.server.module.database.DatabaseModule;
import com.github.jenya705.mcapi.server.module.database.DatabaseModuleImpl;
import com.github.jenya705.mcapi.server.module.database.DatabaseTypeInitializer;
import com.google.inject.ImplementedBy;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * Database module which connect with SQL Database
 *
 * @author Jenya705
 */
@ImplementedBy(DatabaseModuleImpl.class)
public interface SQLDatabaseModule extends DatabaseModule, SQLConnectionManager {

    void addTypeInitializer(String type, DatabaseTypeInitializer typeInitializer);

    void update(String sql, Object... objects);

    ResultSet query(String sql, Object... objects);

}
