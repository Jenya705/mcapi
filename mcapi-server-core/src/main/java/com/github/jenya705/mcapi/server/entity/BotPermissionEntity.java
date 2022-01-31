package com.github.jenya705.mcapi.server.entity;

import lombok.Builder;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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
    private final boolean toggled;

    public BotPermissionEntity(int botId, String permission, UUID target, boolean regex, boolean toggled) {
        this.botId = botId;
        this.permission = permission;
        this.target = Objects.equals(target, identityTarget) ? null : target;
        this.regex = regex;
        this.toggled = toggled;
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

    public static String toRegex(String permission) {
        return permission
                .replace(".", "\\.")
                .replace("*", ".*");
    }

}
