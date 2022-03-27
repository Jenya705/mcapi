package com.github.jenya705.mcapi.server.module.database;

import java.sql.Connection;

/**
 *
 * Database module which is containing connection with SQL database.
 *
 * @author Jenya705
 */
public interface SQLDatabaseModule extends DatabaseModule {

    Connection getConnection();

}
