package com.github.jenya705.mcapi.bukkit.block;

import com.github.jenya705.mcapi.bukkit.BukkitUtils;
import lombok.Getter;
import lombok.experimental.Delegate;
import lombok.experimental.UtilityClass;

/**
 * @author Jenya705
 */
@UtilityClass
public class BukkitSnapshotBlock {

    @Getter
    public static class BlockSnapshot implements org.bukkit.block.Block {

        interface Rewritten {
            org.bukkit.block.data.BlockData getBlockData();
            org.bukkit.block.BlockState getState();
        }

        @Delegate(excludes = Rewritten.class)
        private final org.bukkit.block.Block block;

        private final org.bukkit.block.data.BlockData blockData;
        private final org.bukkit.block.BlockState state;

        public BlockSnapshot(org.bukkit.block.Block block) {
            this.block = block;
            blockData = this.block.getBlockData().clone();
            state = BukkitUtils.notAsyncSupplier(() -> this.block.getState(true));
        }

    }

    public BukkitBlockWrapper of(org.bukkit.block.Block block) {
        return block == null ? null : BukkitBlockWrapper.of(new BlockSnapshot(block));
    }

}
