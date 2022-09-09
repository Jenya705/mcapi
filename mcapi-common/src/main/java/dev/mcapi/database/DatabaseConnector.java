package dev.mcapi.database;

import java.sql.Connection;

public interface DatabaseConnector {

    Connection getConnection();

}
