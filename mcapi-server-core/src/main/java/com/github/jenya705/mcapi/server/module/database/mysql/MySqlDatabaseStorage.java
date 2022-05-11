package com.github.jenya705.mcapi.server.module.database.mysql;

import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.entity.BotEntity;
import com.github.jenya705.mcapi.server.entity.BotPermissionEntity;
import com.github.jenya705.mcapi.server.module.database.sql.SQLDatabaseModule;
import com.github.jenya705.mcapi.server.module.database.storage.DatabaseStorageImpl;
import com.github.jenya705.mcapi.server.module.storage.StorageModule;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author Jenya705
 */
public class MySqlDatabaseStorage extends DatabaseStorageImpl {

    public MySqlDatabaseStorage(ServerApplication application, SQLDatabaseModule databaseModule, StorageModule storageModule) throws IOException {
        super(application, databaseModule, storageModule, "mysql");
    }

    @Override
    @SneakyThrows
    public List<BotEntity> findBotsPageByOwner(UUID owner, int page, int size) {
        return BotEntity.mapResultSet(
                getDatabaseModule().query(
                        getFindBotsPageByOwner(),
                        owner.getMostSignificantBits(),
                        owner.getLeastSignificantBits(),
                        page * size,
                        size
                )
        );
    }

    @Override
    @SneakyThrows
    public List<BotPermissionEntity> findPermissionsPageById(int id, int page, int size) {
        return BotPermissionEntity.mapResultSet(
                getDatabaseModule().query(
                        getFindPermissionsPageById(),
                        id, page * size, size
                )
        );
    }
}
