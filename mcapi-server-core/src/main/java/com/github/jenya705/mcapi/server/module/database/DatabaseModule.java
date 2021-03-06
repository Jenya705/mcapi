package com.github.jenya705.mcapi.server.module.database;

import com.github.jenya705.mcapi.server.module.database.cache.CacheStorage;
import com.github.jenya705.mcapi.server.module.database.safe.DatabaseGetter;
import com.github.jenya705.mcapi.server.module.database.storage.DatabaseStorage;
import com.google.inject.ImplementedBy;

import java.sql.ResultSet;

/**
 * @author Jenya705
 */
@ImplementedBy(DatabaseModuleImpl.class)
public interface DatabaseModule {

    DatabaseStorage storage();

    CacheStorage cache();

    DatabaseGetter safeSync();

    DatabaseGetter safeSyncWithFuture();

    DatabaseGetter safeAsync();

}
