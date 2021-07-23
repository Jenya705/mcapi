package com.github.jenya705.mcapi.database;

import com.github.jenya705.mcapi.ApiServerApplication;
import com.github.jenya705.mcapi.ApiServerConfiguration;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.Locale;
import java.util.Map;

/**
 * @since 1.0
 * @author Jenya705
 */
@Slf4j
@Getter
public class ApiServerDatabaseImpl implements ApiServerDatabase {

    private static final Map<String, String> drivers = Map.of(
            "mysql", "com.mysql.cj.jdbc.Driver"
    );

    public static void loadDriver(String sqlType) throws ClassNotFoundException, NullPointerException {
        Class.forName(drivers.get(sqlType.toLowerCase(Locale.ROOT)));
    }

    private final Connection connection;

    public ApiServerDatabaseImpl() {
        ApiServerApplication application = ApiServerApplication.getApplication();
        ApiServerConfiguration configuration = application.getCore().getConfig();
        try {
            loadDriver(configuration.getSqlType());
        } catch (ClassNotFoundException | NullPointerException e) {
            log.error(String.format("Can not load jdbc driver for %s:", configuration.getSqlType()), e);
            throw new IllegalArgumentException(e);
        }
        try {
            connection = DriverManager.getConnection(
                    String.format("jdbc:%s://%s/%s",
                            configuration.getSqlType(),
                            configuration.getSqlHost(),
                            configuration.getSqlDatabase()
                    ),
                    configuration.getSqlUser(),
                    configuration.getSqlPassword()
            );
        } catch (SQLException e) {
            log.error("Can not create connection:", e);
            throw new IllegalArgumentException(e);
        }
        log.info("Connection created");
    }

    @Override
    public ResultSet query(String sql, Object... args) throws SQLException {
        if (args.length == 0) {
            return connection.createStatement().executeQuery(sql);
        }
        PreparedStatement statement = connection.prepareStatement(sql);
        for (int i = 0; i < args.length; ++i) statement.setObject(i+1, args[i]);
        return statement.executeQuery();
    }

    @Override
    public void update(String sql, Object... args) throws SQLException{
        if (args.length == 0) {
            connection.createStatement().executeUpdate(sql);
            return;
        }
        PreparedStatement statement = connection.prepareStatement(sql);
        for (int i = 0; i < args.length; ++i) statement.setObject(i+1, args[i]);
        statement.executeUpdate();
    }

}
