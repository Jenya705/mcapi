package com.github.jenya705.mcapi.inventory;

import com.github.jenya705.mcapi.BukkitWrapper;
import com.github.jenya705.mcapi.Material;
import com.github.jenya705.mcapi.VanillaMaterial;
import com.github.jenya705.mcapi.player.Player;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * @author Jenya705
 */
@Getter
public class BukkitUniqueInventoryViewWrapper implements BukkitInventoryViewWrapper {

    private final Collection<Player> viewers;
    private final Inventory inventory;
    private final Material airMaterial;

    public BukkitUniqueInventoryViewWrapper(Inventory inventory, Material airMaterial) {
        this.inventory = inventory;
        this.airMaterial = airMaterial;
        viewers = new ArrayList<>();
    }

    public static BukkitUniqueInventoryViewWrapper of(Inventory inventory, Material airMaterial) {
        if (inventory == null) return null;
        return new BukkitUniqueInventoryViewWrapper(
                inventory,
                Objects.requireNonNullElse(airMaterial, VanillaMaterial.AIR)
        );
    }

    @Override
    public org.bukkit.inventory.Inventory getBukkitInventory() {
        return BukkitWrapper.copyInventory(this);
    }

    @Override
    public void open(Player player) {
        viewers.add(player);
        player.openInventory(this, false);
    }

    @Override
    public boolean isUnique() {
        return true;
    }
}
