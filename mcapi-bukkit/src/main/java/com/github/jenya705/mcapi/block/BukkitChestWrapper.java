package com.github.jenya705.mcapi.block;

import com.github.jenya705.mcapi.BukkitWrapper;
import com.github.jenya705.mcapi.Player;
import com.github.jenya705.mcapi.inventory.Inventory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
public class BukkitChestWrapper implements Chest {

    private final org.bukkit.block.Chest chest;

    private final Inventory inventoryWrapper;

    public BukkitChestWrapper(org.bukkit.block.Chest chest) {
        this.chest = chest;
        inventoryWrapper = BukkitWrapper.inventory(chest.getInventory());
    }

    public static BukkitChestWrapper of(org.bukkit.block.Chest chest) {
        if (chest == null) return null;
        return new BukkitChestWrapper(chest);
    }

    @Override
    public List<? extends Player> getWatchers() {
        return chest
                .getInventory()
                .getViewers()
                .stream()
                .filter(it -> it instanceof org.bukkit.entity.Player)
                .map(it -> BukkitWrapper.player(
                        (org.bukkit.entity.Player) it)
                )
                .collect(Collectors.toList());
    }

    @Override
    public Inventory getInventory() {
        return inventoryWrapper;
    }
}
