package com.github.jenya705.mcapi.entity;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class BotEntity {

    private String token;
    private String name;
    private UUID owner;
    private int id;

    public static BotEntity empty() {
        return new BotEntity(null, null, null, -1);
    }

    public static List<BotEntity> mapResultSet(ResultSet resultSet) throws SQLException {
        List<BotEntity> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(new BotEntity(
                    resultSet.getString("token"),
                    resultSet.getString("name"),
                    new UUID(
                            resultSet.getLong("owner_most"),
                            resultSet.getLong("owner_least")
                    ),
                    resultSet.getInt("id")
            ));
        }
        return result;
    }

    public boolean isEmpty() {
        return id == -1;
    }

}
