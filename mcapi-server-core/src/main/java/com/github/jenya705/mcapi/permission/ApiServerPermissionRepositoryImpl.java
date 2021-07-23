package com.github.jenya705.mcapi.permission;

import com.github.jenya705.mcapi.ApiPermissionEntity;
import com.github.jenya705.mcapi.ApiServerApplication;
import com.github.jenya705.mcapi.database.ApiServerDatabase;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @since 1.0
 * @author Jenya705
 */
@Slf4j
public class ApiServerPermissionRepositoryImpl implements ApiServerPermissionRepository {

    protected static ApiServerDatabase getDatabase() {
        return ApiServerApplication.getApplication().getDatabase();
    }

    protected static ApiPermissionEntity map(ResultSet result) throws SQLException {
        return SynchronizedApiServerPermissionEntity.target(
                result.getString("name"),
                result.getString("target"),
                result.getString("token"),
                result.getBoolean("enabled")
        );
    }

    protected static List<ApiPermissionEntity> mapAll(ResultSet result) throws SQLException {
        List<ApiPermissionEntity> entities = new ArrayList<>();
        while (result.next()) {
            entities.add(map(result));
        }
        return entities;
    }

    public ApiServerPermissionRepositoryImpl() {
        try {
            getDatabase().update(
                    "create table if not exists mcapi_permission (\n" +
                            "    name varchar(1024) not null,\n" +
                            "    token char(85) not null,\n" +
                            "    target varchar(64) default null,\n" +
                            "    enabled boolean\n" +
                            ");"
            );
        } catch (SQLException e) {
            log.error("Can not instantiate permission repository:", e);
            throw new RuntimeException(e);
        }
        log.info("Initialized permission repository");
    }

    @Override
    @SneakyThrows
    public void save(ApiPermissionEntity entity) {
        getDatabase().query(
                "insert into mcapi_permission (name, token, target, enabled) values (?, ?, ?, ?)",
                entity.getName(), entity.getToken(), entity.getTarget(), entity.isEnabled()
        );
    }

    @Override
    public void saveAll(Collection<ApiPermissionEntity> entities) {
        entities.forEach(this::save);
    }

    @Override
    @SneakyThrows
    public ApiPermissionEntity getTarget(String name, String target, String token) {
        List<ApiPermissionEntity> entities = mapAll(getDatabase().query(
                "select * from mcapi_permission where name = ? and target = ? and token = ?",
                name, target, token
        ));
        if (entities.isEmpty()) return null;
        return entities.get(0);
    }

    @Override
    public ApiPermissionEntity getGlobal(String name, String token) {
        return getTarget(name, null, token);
    }

    @Override
    @SneakyThrows
    public void updateTarget(String name, String target, String token, boolean enabled) {
        getDatabase().update(
                "update mcapi_permission set enabled = ? where name = ? and target = ? and token = ?",
                enabled, name, target, token
        );
    }

    @Override
    public void updateGlobal(String name, String token, boolean enabled) {
        updateTarget(name, null, token, enabled);
    }
}
