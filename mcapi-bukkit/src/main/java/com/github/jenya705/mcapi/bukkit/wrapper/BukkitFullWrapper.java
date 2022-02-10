package com.github.jenya705.mcapi.bukkit.wrapper;

import com.github.jenya705.mcapi.block.Block;
import com.github.jenya705.mcapi.block.BlockData;
import com.github.jenya705.mcapi.bukkit.block.BukkitBlockDataRegistry;
import com.github.jenya705.mcapi.bukkit.player.BukkitPlayerWrapper;
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

    public Object holderToLocal(Object bukkit) {
        if (bukkit instanceof org.bukkit.entity.Entity entity) {
            return BukkitWrapper.entity(entity);
        }
        if (bukkit instanceof org.bukkit.block.BlockState state) {
           return blockDataRegistry.getData(state.getBlock());
        }
        if (bukkit instanceof org.bukkit.block.Block block) {
            return BukkitWrapper.block(block);
        }
        return null;
    }

    public Object holderToBukkit(Object local) {
        if (local instanceof Player player) {
            if (player instanceof BukkitPlayerWrapper playerWrapper) {
                return playerWrapper;
            }
            return BukkitPlayerWrapper.of(Bukkit.getPlayer(player.getUuid()));
        }
        if (local instanceof BlockData data) {
            return BukkitWrapper.block(data.getBlock());
        }
        if (local instanceof Block block) {
            return BukkitWrapper.block(block);
        }
        return null;
    }
}
