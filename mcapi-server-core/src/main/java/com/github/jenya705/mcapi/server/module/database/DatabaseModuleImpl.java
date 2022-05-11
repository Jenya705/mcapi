package com.github.jenya705.mcapi.server.module.database;

import com.github.jenya705.mcapi.server.application.AbstractApplicationModule;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.data.ConfigData;
import com.github.jenya705.mcapi.server.log.TimerTask;
import com.github.jenya705.mcapi.server.module.config.ConfigModule;
import com.github.jenya705.mcapi.server.module.database.cache.CacheConfig;
import com.github.jenya705.mcapi.server.module.database.cache.CacheStorage;
import com.github.jenya705.mcapi.server.module.database.cache.CacheStorageImpl;
import com.github.jenya705.mcapi.server.module.database.cache.FakeCacheStorage;
import com.github.jenya705.mcapi.server.module.database.mysql.MySqlDatabaseInitializer;
import com.github.jenya705.mcapi.server.module.database.safe.CacheDatabaseGetter;
import com.github.jenya705.mcapi.server.module.database.safe.DatabaseGetter;
import com.github.jenya705.mcapi.server.module.database.safe.StorageDatabaseGetter;
import com.github.jenya705.mcapi.server.module.database.sql.SQLConnectionManager;
import com.github.jenya705.mcapi.server.module.database.sql.SQLDatabaseModule;
import com.github.jenya705.mcapi.server.module.database.sqlite.SqliteDatabaseInitializer;
import com.github.jenya705.mcapi.server.module.database.storage.DatabaseStorage;
import com.github.jenya705.mcapi.server.module.storage.StorageModule;
import com.github.jenya705.mcapi.server.util.LazyInitializer;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Data;
import lombok.SneakyThrows;
import org.slf4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * @author Jenya705
 */
@Singleton
public class DatabaseModuleImpl extends AbstractApplicationModule implements SQLDatabaseModule {

    @Data
    private static class Late {
        private CacheStorage cache;
        private DatabaseGetter safeSync;
        private DatabaseGetter safeSyncWithFuture;
        private DatabaseGetter safeAsync;
        private DatabaseStorage storage;
        private SQLConnectionManager connectionManager;
    }

    private final ServerApplication application;
    private final DefaultDatabaseTypeInitializer defaultDatabaseTypeInitializer;

    private final DatabaseModuleConfig config;
    private final CacheConfig cacheConfig;

    private final Logger log;

    private final Map<String, DatabaseTypeInitializer> databaseTypeInitializers = new ConcurrentHashMap<>();
    private final Supplier<Late> lateSupplier = new LazyInitializer<>(this::createLate, late -> late.getStorage().setup());

    private Late createLate() {
        Late result = new Late();
        TimerTask task = TimerTask.start(log, "Creating connection with database...");
        result.setConnectionManager(createConnectionManager());
        result.setStorage(createStorage(config.getType()));
        result.setSafeAsync(new StorageDatabaseGetter(result.getStorage()));
        if (config.isCacheEnabled()) {
            result.setCache(
                    new CacheStorageImpl(
                            application,
                            cacheConfig,
                            this
                    )
            );
            result.setSafeSync(new CacheDatabaseGetter(result.getCache()));
            result.setSafeSyncWithFuture(new CacheDatabaseGetter(result.getCache().withFuture()));
        }
        else {
            result.setCache(new FakeCacheStorage(this));
            result.setSafeSync(result.getSafeAsync());
            result.setSafeSyncWithFuture(result.getSafeSync());
        }
        task.complete();
        return result;
    }

    @Inject
    public DatabaseModuleImpl(ServerApplication application, ConfigModule configModule, StorageModule storageModule, Logger log) {
        super(application);
        this.log = log;
        this.application = application;
        addTypeInitializer("mysql", new MySqlDatabaseInitializer(app(), this, storageModule));
        addTypeInitializer("sqlite", new SqliteDatabaseInitializer(app(), this, storageModule));
        defaultDatabaseTypeInitializer = new DefaultDatabaseTypeInitializer(app(), this, storageModule);
        ConfigData configData = configModule
                .getConfig()
                .required("database");
        config = new DatabaseModuleConfig(configData);
        cacheConfig = new CacheConfig(configData.required("cache"));
    }

    protected SQLConnectionManager createConnectionManager() {
        SQLConnectionManager connection = null;
        String loweredType = config.getType().toLowerCase(Locale.ROOT);
        if (databaseTypeInitializers.containsKey(loweredType)) {
            connection = databaseTypeInitializers
                    .get(loweredType)
                    .connection(
                            config.getHost(),
                            config.getUser(),
                            config.getPassword(),
                            config.getDatabase()
                    );
        }
        if (connection == null) {
            connection = defaultDatabaseTypeInitializer
                    .connection(config);
        }
        return connection;
    }

    @Override
    @SneakyThrows
    public void update(String sql, Object... objects) {
        debugSql(sql, objects);
        doWithConnection(connection -> {
            if (objects.length == 0) {
                return connection.createStatement().executeUpdate(sql);
            }
            else {
                PreparedStatement statement = connection.prepareStatement(sql);
                for (int i = 0; i < objects.length; ++i) {
                    statement.setObject(i + 1, objects[i]);
                }
                return statement.executeUpdate();
            }
        });
    }

    @Override
    @SneakyThrows
    public ResultSet query(String sql, Object... objects) {
        debugSql(sql, objects);
        return doWithConnection(connection -> {
            if (objects.length == 0) {
                return connection.createStatement().executeQuery(sql);
            }
            else {
                PreparedStatement statement = connection.prepareStatement(sql);
                for (int i = 0; i < objects.length; ++i) {
                    statement.setObject(i + 1, objects[i]);
                }
                return statement.executeQuery();
            }
        });
    }

    @Override
    public DatabaseStorage storage() {
        return lateSupplier.get().getStorage();
    }

    @Override
    public CacheStorage cache() {
        return lateSupplier.get().getCache();
    }

    private DatabaseStorage createStorage(String sqlType) {
        String loweredSqlType = sqlType.toLowerCase(Locale.ROOT);
        DatabaseStorage created = null;
        if (databaseTypeInitializers.containsKey(loweredSqlType)) {
            created = databaseTypeInitializers
                    .get(loweredSqlType)
                    .storage();
        }
        if (created == null) {
            created = defaultDatabaseTypeInitializer.storage(config);
        }
        return created;
    }

    private void debugSql(String sql, Object... objects) {
        if (debug()) {
            log.info("SQL: {}#{}", sql, Arrays.toString(objects));
        }
    }

    @Override
    public DatabaseGetter safeSync() {
        return lateSupplier.get().getSafeSync();
    }

    @Override
    public DatabaseGetter safeSyncWithFuture() {
        return lateSupplier.get().getSafeSyncWithFuture();
    }

    @Override
    public DatabaseGetter safeAsync() {
        return lateSupplier.get().getSafeAsync();
    }

    @Override
    public void doWithConnection(ConnectionConsumer connectionConsumer) throws SQLException {
        lateSupplier.get().getConnectionManager().doWithConnection(connectionConsumer);
    }

    @Override
    public <T> T doWithConnection(ConnectionFunction<T> connectionFunction) throws SQLException {
        return lateSupplier.get().getConnectionManager().doWithConnection(connectionFunction);
    }

    @Override
    public void addTypeInitializer(String type, DatabaseTypeInitializer typeInitializer) {
        databaseTypeInitializers.put(type.toLowerCase(Locale.ROOT), typeInitializer);
    }
}
