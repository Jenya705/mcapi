package com.github.jenya705.mcapi.server.module.database.sql;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * Manage connection with sql server.
 *
 * @author Jenya705
 */
public interface SQLConnectionManager {

    @FunctionalInterface
    interface ConnectionConsumer {
        void accept(Connection connection) throws SQLException;
    }

    @FunctionalInterface
    interface ConnectionFunction<T> {
        T apply(Connection connection) throws SQLException;
    }

    void doWithConnection(ConnectionConsumer connectionConsumer) throws SQLException;

    <T> T doWithConnection(ConnectionFunction<T> connectionFunction) throws SQLException;

}
