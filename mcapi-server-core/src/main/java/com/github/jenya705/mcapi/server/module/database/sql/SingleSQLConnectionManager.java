package com.github.jenya705.mcapi.server.module.database.sql;

import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Jenya705
 */
@RequiredArgsConstructor
public class SingleSQLConnectionManager implements SQLConnectionManager {

    private final Connection connection;

    @Override
    public void doWithConnection(ConnectionConsumer connectionConsumer) throws SQLException {
        synchronized (connection) {
            connectionConsumer.accept(connection);
        }
    }

    @Override
    public <T> T doWithConnection(ConnectionFunction<T> connectionFunction) throws SQLException {
        synchronized (connection) {
            return connectionFunction.apply(connection);
        }
    }
}
