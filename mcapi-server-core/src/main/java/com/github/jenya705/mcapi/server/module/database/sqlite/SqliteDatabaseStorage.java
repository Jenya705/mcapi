package com.github.jenya705.mcapi.server.module.database.sqlite;

import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.entity.BotPermissionEntity;
import com.github.jenya705.mcapi.server.module.database.sql.SQLDatabaseModule;
import com.github.jenya705.mcapi.server.module.database.storage.DatabaseStorageImpl;
import com.github.jenya705.mcapi.server.module.storage.StorageModule;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Jenya705
 */
public class SqliteDatabaseStorage extends DatabaseStorageImpl {

    public SqliteDatabaseStorage(ServerApplication application, SQLDatabaseModule databaseModule, StorageModule storageModule) throws IOException {
        super(application, databaseModule, storageModule, "sqlite");
    }

    @Override
    @SneakyThrows
    public BotPermissionEntity findPermission(int botId, String permission, UUID target) {
        UUID realTarget = parseTarget(target);
        BotPermissionEntity permissionEntity =
                isCacheFake() ? null : cache().getPermission(botId, permission, target);
        if (permissionEntity == null) {
            List<BotPermissionEntity> permissionEntities =
                    BotPermissionEntity.mapResultSet(
                            getDatabaseModule().query(
                                    getFindPermission(),
                                    botId,
                                    realTarget.getMostSignificantBits(),
                                    realTarget.getLeastSignificantBits()
                            )
                    );
            permissionEntity = Objects.requireNonNullElseGet(
                    chooseRightPermission(target, permissionEntities, entity -> permission.matches(entity.getPermission())),
                    () -> new BotPermissionEntity(
                            botId,
                            permission,
                            target,
                            false,
                            getStorageModule()
                    )
            );
            if (!isCacheFake()) {
                cache().cache(permissionEntity);
            }
        }
        return permissionEntity;
    }
}
