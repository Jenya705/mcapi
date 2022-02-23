package com.github.jenya705.mcapi.bukkit.block.state;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

/**
 * @author Jenya705
 */
public interface CapturedState {

    Block getBlock();

    BlockState getState();

    <T extends BlockState> T state();

    void updateState();

}
