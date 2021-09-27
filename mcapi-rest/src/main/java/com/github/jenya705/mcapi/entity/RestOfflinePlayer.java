package com.github.jenya705.mcapi.entity;

import com.github.jenya705.mcapi.ApiOfflinePlayer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestOfflinePlayer {

    private String name;
    private UUID uuid;

    public static RestOfflinePlayer from(ApiOfflinePlayer player) {
        return new RestOfflinePlayer(
                player.getName(),
                player.getUuid()
        );
    }
}
