package com.github.jenya705.mcapi.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Jenya705
 */
@Data
@AllArgsConstructor
public class BotLinkEntity {

    private final int botId;
    private final UUID target;

    public static List<BotLinkEntity> mapResultSet(ResultSet resultSet) throws SQLException {
        List<BotLinkEntity> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(new BotLinkEntity(
                    resultSet.getInt("bot_id"),
                    new UUID(
                            resultSet.getLong("target_most"),
                            resultSet.getLong("target_least")
                    )
            ));
        }
        return result;
    }

}
