package com.github.jenya705.mcapi.bukkit.block.state;

import org.bukkit.block.BlockState;

/**
 * @author Jenya705
 */
public interface CapturedState {

    BlockState getState();

    <T extends BlockState> T state();

}
