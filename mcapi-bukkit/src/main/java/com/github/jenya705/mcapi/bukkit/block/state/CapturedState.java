package com.github.jenya705.mcapi.bukkit.block.state;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

import java.util.function.Consumer;

/**
 * @author Jenya705
 */
public interface CapturedState {

    Block getBlock();

    BlockState getState();

    <T extends BlockState> T state();

    void reloadState();

    void updateState();

}
