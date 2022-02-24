package com.github.jenya705.mcapi.bukkit.wrapper;

import com.github.jenya705.mcapi.block.BlockData;
import com.github.jenya705.mcapi.bukkit.BukkitUtils;
import com.github.jenya705.mcapi.bukkit.block.BukkitBlockDataRegistry;
import com.github.jenya705.mcapi.bukkit.block.BukkitStateContainer;
import com.github.jenya705.mcapi.bukkit.player.BukkitPlayerWrapper;
import com.github.jenya705.mcapi.entity.Entity;
import com.github.jenya705.mcapi.inventory.InventoryHolder;
import com.github.jenya705.mcapi.player.Player;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bukkit.Bukkit;

/**
 * @author Jenya705
 */
@Singleton
public class BukkitFullWrapper {

    private final BukkitBlockDataRegistry blockDataRegistry;

    @Inject
    public BukkitFullWrapper(BukkitBlockDataRegistry blockDataRegistry) {
        this.blockDataRegistry = blockDataRegistry;
    }

    public InventoryHolder holderToLocal(org.bukkit.inventory.InventoryHolder bukkit) {
        if (bukkit instanceof org.bukkit.entity.Entity entity) {
            Entity wrappedEntity = BukkitWrapper.entity(entity);
            return wrappedEntity instanceof InventoryHolder ?
                    (InventoryHolder) wrappedEntity : null;
        }
        if (bukkit instanceof org.bukkit.block.BlockState state) {
            BlockData wrappedBlockData = blockDataRegistry.getData(state.getBlock());
            return wrappedBlockData instanceof InventoryHolder ?
                    (InventoryHolder) wrappedBlockData : null;
        }
        return null;
    }

    public org.bukkit.inventory.InventoryHolder holderToBukkit(InventoryHolder local) {
        if (local instanceof Player player) {
            if (player instanceof BukkitPlayerWrapper playerWrapper) {
                return playerWrapper.getBukkit();
            }
            return Bukkit.getPlayer(player.getUuid());
        }
        if (local instanceof BlockData data) {
            if (data instanceof BukkitStateContainer stateContainer) {
                return stateContainer.getState().state();
            }
            return (org.bukkit.inventory.InventoryHolder) BukkitUtils
                    .notAsyncSupplier(() -> BukkitWrapper.block(data.getBlock()).getState());
        }
        return null;
    }
}
