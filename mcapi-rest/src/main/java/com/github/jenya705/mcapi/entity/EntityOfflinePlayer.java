package com.github.jenya705.mcapi.entity;

import com.github.jenya705.mcapi.player.OfflinePlayer;
import com.github.jenya705.mcapi.rest.RestOfflinePlayer;
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
public class EntityOfflinePlayer implements OfflinePlayer {

    private String name;
    private UUID uuid;
    private boolean online;

    @Override
    public void ban(String reason) {
        EntityUtils.throwEntityContextException();
    }

    public RestOfflinePlayer rest() {
        return RestOfflinePlayer.from(this);
    }
}
