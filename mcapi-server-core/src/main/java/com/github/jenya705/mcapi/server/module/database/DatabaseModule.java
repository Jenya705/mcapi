package com.github.jenya705.mcapi.server.module.database;

import com.github.jenya705.mcapi.server.module.database.cache.CacheStorage;
import com.github.jenya705.mcapi.server.module.database.safe.DatabaseGetter;
import com.google.inject.ImplementedBy;

import java.sql.ResultSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Jenya705
 */
@ImplementedBy(DatabaseModuleImpl.class)
public interface DatabaseModule {

    ExecutorService async = Executors.newSingleThreadExecutor();

    void update(String sql, Object... objects);

    ResultSet query(String sql, Object... objects);

    DatabaseStorage storage();

    CacheStorage cache();

    DatabaseGetter safeSync();

    DatabaseGetter safeSyncWithFuture();

    DatabaseGetter safeAsync();

    void addTypeInitializer(String type, DatabaseTypeInitializer typeInitializer);

}
