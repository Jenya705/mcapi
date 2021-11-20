package com.github.jenya705.mcapi.entity.player;

import com.github.jenya705.mcapi.entity.EntityUtils;
import com.github.jenya705.mcapi.player.GameMode;
import com.github.jenya705.mcapi.Location;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.inventory.Inventory;
import com.github.jenya705.mcapi.inventory.PlayerInventory;
import com.github.jenya705.mcapi.player.PlayerAbilities;
import com.github.jenya705.mcapi.rest.player.RestPlayer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.kyori.adventure.text.Component;

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
    private Inventory enderChest;
    private float yaw;
    private float pitch;
    private GameMode gameMode;
    private PlayerAbilities abilities;

    @Override
    public void sendMessage(String message) {
        EntityUtils.throwEntityContextException();
    }

    @Override
    public void sendMessage(Component component) {
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
