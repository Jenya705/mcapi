package com.github.jenya705.mcapi.server.module.database;

import com.google.inject.ImplementedBy;

import java.sql.Connection;
import java.sql.ResultSet;

/**
 *
 * Database module which is containing connection with SQL database.
 *
 * @author Jenya705
 */
@ImplementedBy(DatabaseModuleImpl.class)
public interface SQLDatabaseModule extends DatabaseModule {

    void addTypeInitializer(String type, DatabaseTypeInitializer typeInitializer);

    void update(String sql, Object... objects);

    ResultSet query(String sql, Object... objects);

    Connection getConnection();

}
