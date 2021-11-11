package com.github.jenya705.mcapi.block.state;

import com.github.jenya705.mcapi.BukkitUtils;
import lombok.RequiredArgsConstructor;
import org.bukkit.block.BlockState;

/**
 * @author Jenya705
 */
@RequiredArgsConstructor
public class SharedCapturedState implements CapturedState {

    private final org.bukkit.block.Block block;

    private BlockState state;

    @Override
    public BlockState getState() {
        if (state == null) {
            state = BukkitUtils.notAsyncSupplier(block::getState);
        }
        return state;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends BlockState> T state() {
        return (T) getState();
    }
}
