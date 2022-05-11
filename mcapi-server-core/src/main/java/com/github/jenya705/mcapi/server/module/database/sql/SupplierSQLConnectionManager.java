package com.github.jenya705.mcapi.server.module.database.sql;

import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Jenya705
 */
@RequiredArgsConstructor
public class SupplierSQLConnectionManager implements SQLConnectionManager {

    @FunctionalInterface
    public interface ConnectionSupplier {
        Connection get() throws SQLException;
    }

    private final ConnectionSupplier connectionSupplier;

    @Override
    public void doWithConnection(SQLDatabaseModule.ConnectionConsumer connectionConsumer) throws SQLException {
        connectionConsumer.accept(connectionSupplier.get());
    }

    @Override
    public <T> T doWithConnection(SQLDatabaseModule.ConnectionFunction<T> connectionFunction) throws SQLException {
        return connectionFunction.apply(connectionSupplier.get());
    }
}
