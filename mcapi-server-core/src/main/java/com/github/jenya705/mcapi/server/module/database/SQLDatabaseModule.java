package com.github.jenya705.mcapi.server.module.database;

import java.sql.Connection;
import java.sql.ResultSet;

/**
 *
 * Database module which is containing connection with SQL database.
 *
 * @author Jenya705
 */
public interface SQLDatabaseModule extends DatabaseModule {

    void update(String sql, Object... objects);

    ResultSet query(String sql, Object... objects);

    Connection getConnection();

}
