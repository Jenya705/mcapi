package com.github.jenya705.mcapi.entity.player;

import com.github.jenya705.mcapi.BoundingBox;
import com.github.jenya705.mcapi.Location;
import com.github.jenya705.mcapi.Vector3;
import com.github.jenya705.mcapi.entity.EntityUtils;
import com.github.jenya705.mcapi.inventory.Inventory;
import com.github.jenya705.mcapi.inventory.InventoryView;
import com.github.jenya705.mcapi.inventory.PlayerInventory;
import com.github.jenya705.mcapi.player.GameMode;
import com.github.jenya705.mcapi.player.Player;
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
@Deprecated
@NoArgsConstructor
@AllArgsConstructor
public class EntityPlayer implements Player {

    private String name;
    private UUID uuid;
    private String type;
    private boolean online;
    private Component customName;
    private Location location;
    private PlayerInventory inventory;
    private Inventory enderChest;
    private GameMode gameMode;
    private PlayerAbilities abilities;

    @Override
    public void kill() {
        EntityUtils.throwEntityContextException();
    }

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
    public InventoryView openInventory(InventoryView inventory, boolean sayAboutSelf) {
        EntityUtils.throwEntityContextException();
        return null;
    }

    @Override
    public Component customName() {
        return customName;
    }

    @Override
    public InventoryView openInventory(Inventory inventory) {
        EntityUtils.throwEntityContextException();
        return null;
    }

    @Override
    public void ban(String reason) {
        EntityUtils.throwEntityContextException();
    }

    @Override
    public void kick(String reason) {
        EntityUtils.throwEntityContextException();
    }

    @Override
    public float getHealth() {
        return 0;
    }

    @Override
    public void chat(String message) {
        EntityUtils.throwEntityContextException();
    }

    @Override
    public void runCommand(String command) {
        EntityUtils.throwEntityContextException();
    }

    public RestPlayer rest() {
        return RestPlayer.from(this);
    }

    @Override
    public boolean hasAI() {
        return false;
    }
}
