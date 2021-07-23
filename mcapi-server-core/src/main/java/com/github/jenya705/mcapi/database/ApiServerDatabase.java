package com.github.jenya705.mcapi.database;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @since 1.0
 * @author Jenya705
 */
public interface ApiServerDatabase {

    ResultSet query(String sql, Object... objects) throws SQLException;

    void update(String sql, Object... objects) throws SQLException;

}
