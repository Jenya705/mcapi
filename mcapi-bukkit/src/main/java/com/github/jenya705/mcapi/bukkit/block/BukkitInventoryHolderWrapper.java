package com.github.jenya705.mcapi.bukkit.block;

import com.github.jenya705.mcapi.bukkit.BukkitWrapper;
import com.github.jenya705.mcapi.bukkit.block.state.CapturedState;
import com.github.jenya705.mcapi.inventory.Inventory;
import com.github.jenya705.mcapi.inventory.InventoryHolder;
import lombok.RequiredArgsConstructor;
import org.bukkit.block.Container;

/**
 * @author Jenya705
 */
@RequiredArgsConstructor
public class BukkitInventoryHolderWrapper implements InventoryHolder {

    private final CapturedState state;

    private Inventory inventory;

    @Override
    public Inventory getInventory() {
        if (inventory == null) {
            // Capturing state
            Container containerState = state.state();
            inventory = BukkitWrapper.inventory(containerState.getInventory());
        }
        return inventory;
    }
}
