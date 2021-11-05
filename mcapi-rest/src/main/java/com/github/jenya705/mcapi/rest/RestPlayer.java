package com.github.jenya705.mcapi.rest;

import com.github.jenya705.mcapi.Player;
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
public class RestPlayer {

    private String name;
    private UUID uuid;
    private String type;

    public static RestPlayer from(Player player) {
        return new RestPlayer(
                player.getName(),
                player.getUuid(),
                player.getType()
        );
    }

}