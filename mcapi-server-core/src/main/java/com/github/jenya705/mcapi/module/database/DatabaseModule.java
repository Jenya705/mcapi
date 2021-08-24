package com.github.jenya705.mcapi.module.database;

import java.sql.ResultSet;

/**
 * @author Jenya705
 */
public interface DatabaseModule {

    void update(String sql, Object... objects);

    ResultSet query(String sql, Object... objects);

}
