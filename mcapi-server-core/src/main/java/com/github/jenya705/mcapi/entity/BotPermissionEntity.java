package com.github.jenya705.mcapi.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Jenya705
 */
@Data
@Builder
@NoArgsConstructor
public class BotPermissionEntity {

    public static final UUID identityTarget = new UUID(0, 0);

    private int botId;
    private String permission;
    private UUID target;
    private boolean toggled;

    public BotPermissionEntity(int botId, String permission, UUID target, boolean toggled) {
        this.botId = botId;
        this.permission = permission;
        this.target = target.equals(identityTarget) ? null : target;
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
                    resultSet.getBoolean("toggled")
            ));
        }
        return result;
    }
}
