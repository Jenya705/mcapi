package com.github.jenya705.mcapi.bukkit.block.state;

import com.github.jenya705.mcapi.bukkit.BukkitUtils;
import com.github.jenya705.mcapi.bukkit.block.BukkitSnapshotBlock;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.block.BlockState;

/**
 * @author Jenya705
 */
public class SharedCapturedState implements CapturedState {

    @Getter
    private final org.bukkit.block.Block block;

    public SharedCapturedState(org.bukkit.block.Block block) {
        this.block = block;
        if (Bukkit.isPrimaryThread()) { // why not?
            state = block.getState();
        }
    }

    private BlockState state;

    @Override
    public BlockState getState() {
        if (state == null) {
            reloadState();
        }
        return state;
    }

    @Override
    public void reloadState() {
        if (block instanceof BukkitSnapshotBlock.BlockSnapshot) {
            state = block.getState();
        }
        else {
            state = BukkitUtils.blockingNotAsyncSupplier(block::getState);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends BlockState> T state() {
        return (T) getState();
    }

    @Override
    public void updateState() {
        if (state == null) return; // nothing to update
        BukkitUtils.notAsyncTask(() -> state.update());
    }
}
