package com.github.jenya705.mcapi.bukkit.block.state;

import com.github.jenya705.mcapi.bukkit.BukkitUtils;
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
            updateState();
        }
        return state;
    }

    @Override
    public void updateState() {
        state = BukkitUtils.notAsyncSupplier(block::getState);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends BlockState> T state() {
        return (T) getState();
    }
}
