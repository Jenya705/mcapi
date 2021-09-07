package com.github.jenya705.mcapi.module.database;

import com.github.jenya705.mcapi.entity.BotEntity;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author Jenya705
 */
public class MySqlDatabaseStorage extends DatabaseStorageImpl {

    public MySqlDatabaseStorage(DatabaseModule databaseModule) throws IOException {
        super(databaseModule, "mysql");
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
}
