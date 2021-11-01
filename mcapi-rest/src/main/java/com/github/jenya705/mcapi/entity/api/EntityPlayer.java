package com.github.jenya705.mcapi.entity.api;

import com.github.jenya705.mcapi.Location;
import com.github.jenya705.mcapi.Player;
import com.github.jenya705.mcapi.entity.RestPlayer;
import com.github.jenya705.mcapi.inventory.PlayerInventory;
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
public class EntityPlayer implements Player {

    private String name;
    private UUID uuid;
    private String type;
    private boolean online;
    private Location location;
    private PlayerInventory inventory;

    @Override
    public void sendMessage(String message) {
        EntityUtils.throwEntityContextException();
    }

    @Override
    public boolean hasPermission(String permission) {
        EntityUtils.throwEntityContextException();
        return false;
    }

    @Override
    public void ban(String reason) {
        EntityUtils.throwEntityContextException();
    }

    @Override
    public void kick(String reason) {
        EntityUtils.throwEntityContextException();
    }

    public RestPlayer rest() {
        return RestPlayer.from(this);
    }
}
