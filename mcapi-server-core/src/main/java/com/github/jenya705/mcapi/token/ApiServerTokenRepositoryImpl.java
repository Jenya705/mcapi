package com.github.jenya705.mcapi.token;

import com.github.jenya705.mcapi.ApiServerApplication;
import com.github.jenya705.mcapi.database.ApiServerDatabase;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * @since 1.0
 * @author Jenya705
 */
@Slf4j
public class ApiServerTokenRepositoryImpl implements ApiServerTokenRepository {

    protected static ApiServerDatabase getDatabase() {
        return ApiServerApplication.getApplication().getDatabase();
    }

    protected static ApiServerTokenHolderEntity map(ResultSet result) throws SQLException {
        return new ApiServerTokenHolderEntity(
                UUID.fromString(result.getString("player_uuid")),
                result.getString("token"),
                result.getString("name")
        );
    }

    protected static List<ApiServerTokenHolderEntity> mapAll(ResultSet result) throws SQLException {
        List<ApiServerTokenHolderEntity> entities = new ArrayList<>();
        while (result.next()) {
            entities.add(map(result));
        }
        return entities;
    }

    public ApiServerTokenRepositoryImpl() {
        try {
            getDatabase().update(
                    "create table if not exists mcapi_token (\n" +
                            "    player_uuid text not null,\n" +
                            "    token char(85) not null,\n" +
                            "    name text not null,\n" +
                            "    primary key(token)\n" +
                            ");"
            );
        } catch (SQLException e) {
            log.error("Can not instantiate token repository:", e);
            throw new RuntimeException(e);
        }
        log.info("Initialized token repository");
    }

    @Override
    @SneakyThrows
    public void save(ApiServerTokenHolderEntity holder) {
        getDatabase().update(
                "insert into mcapi_token (player_uuid, token) values (?, ?)",
                holder.getPlayer().toString(), holder.getToken()
        );
    }

    @Override
    public void saveAll(Collection<ApiServerTokenHolderEntity> holders) {
        holders.forEach(this::save);
    }

    @Override
    @SneakyThrows
    public ApiServerTokenHolderEntity getHolderByToken(String token) {
        List<ApiServerTokenHolderEntity> entities = mapAll(getDatabase().query(
                "select * from mcapi_token where token = ?", token
        ));
        if (entities.isEmpty()) return null;
        return entities.get(0);
    }

    @Override
    @SneakyThrows
    public List<ApiServerTokenHolderEntity> getHoldersByUUID(UUID player) {
        return mapAll(getDatabase().query(
                "select * from mcapi_token where player_uuid = ?", player.toString()
        ));
    }
}
