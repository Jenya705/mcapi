package com.github.jenya705.mcapi.server.entity;

import com.github.jenya705.mcapi.permission.PermissionFlag;
import com.github.jenya705.mcapi.server.module.storage.StorageModule;
import lombok.Builder;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * @author Jenya705
 */
@Data
@Builder
public class BotPermissionEntity {

    public static final UUID identityTarget = new UUID(0, 0);

    private final int botId;
    private final String permission;
    private final UUID target;
    private final boolean regex;
    private final PermissionFlag permissionFlag;
    private final boolean toggled;

    public BotPermissionEntity(int botId, String permission, UUID target, boolean regex, PermissionFlag permissionFlag, boolean toggled) {
        this.botId = botId;
        this.permission = permission;
        this.target = Objects.equals(target, identityTarget) ? null : target;
        this.regex = regex;
        this.permissionFlag = permissionFlag;
        this.toggled = toggled;
    }

    public BotPermissionEntity(int botId, String permission, UUID target, boolean regex, boolean toggled) {
        this(botId, permission, target, regex, PermissionFlag.of(toggled), toggled);
    }

    public BotPermissionEntity(int botId, String permission, UUID target, boolean regex, StorageModule storageModule) {
        this(botId, permission, target, regex, PermissionFlag.UNDEFINED, isEnabled(permission, storageModule));
    }

    public static List<BotPermissionEntity> mapResultSet(ResultSet resultSet) throws SQLException {
        List<BotPermissionEntity> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(new BotPermissionEntity(
                    resultSet.getInt("bot_id"),
                    resultSet.getString("permission"),
                    new UUID(
                            resultSet.getLong("target_most"),
                            resultSet.getLong("target_least")
                    ),
                    resultSet.getBoolean("regex"),
                    resultSet.getBoolean("toggled")
            ));
        }
        return result;
    }

    public String toLocalPermission() {
        if (regex) {
            return permission;
        }
        return permission
                .replace("\\.", ".")
                .replace(".*", "*");
    }

    public boolean isGlobal() {
        return target == null || target.equals(identityTarget);
    }

    /**
     *
     * Do the same as {@link PermissionFlag#join(PermissionFlag)} but with permission entities
     *
     * @param permissionEntity Other permission
     * @return Leader permission entity
     */
    public BotPermissionEntity join(BotPermissionEntity permissionEntity) {
        return permissionEntity.permissionFlag == PermissionFlag.UNDEFINED ? this : permissionEntity;
    }

    /**
     *
     * Do the same as {@link PermissionFlag#join(Supplier)} but with permission entities
     *
     * @param permissionEntity Other permission
     * @return Leader permission entity
     */
    public BotPermissionEntity join(Supplier<BotPermissionEntity> permissionEntity) {
        if (permissionFlag == PermissionFlag.UNDEFINED) {
            BotPermissionEntity other = permissionEntity.get();
            return join(other);
        }
        return this;
    }

    public static boolean isEnabled(String permission, StorageModule storageModule) {
        PermissionEntity permissionEntity = storageModule.getPermission(permission);
        return permissionEntity != null && permissionEntity.isEnabled();
    }

    public static String toRegex(String permission) {
        return permission
                .replace(".", "\\.")
                .replace("*", ".*");
    }

}
