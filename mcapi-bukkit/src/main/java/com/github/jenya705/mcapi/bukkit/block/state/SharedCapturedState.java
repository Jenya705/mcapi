package com.github.jenya705.mcapi.bukkit.block.state;

import com.github.jenya705.mcapi.bukkit.BukkitUtils;
import com.github.jenya705.mcapi.bukkit.block.BukkitSnapshotBlock;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.block.BlockState;

/**
 * @author Jenya705
 */
@RequiredArgsConstructor
public class SharedCapturedState implements CapturedState {

    @Getter
    private final org.bukkit.block.Block block;

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
