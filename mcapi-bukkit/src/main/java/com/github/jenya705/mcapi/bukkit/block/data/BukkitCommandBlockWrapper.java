package com.github.jenya705.mcapi.bukkit.block.data;

import com.github.jenya705.mcapi.bukkit.block.BukkitDirectionalWrapper;
import com.github.jenya705.mcapi.block.Directional;
import com.github.jenya705.mcapi.block.data.CommandBlock;
import com.github.jenya705.mcapi.bukkit.block.state.CapturedState;
import com.github.jenya705.mcapi.bukkit.block.state.SharedCapturedState;
import lombok.experimental.Delegate;

/**
 * @author Jenya705
 */
public class BukkitCommandBlockWrapper implements CommandBlock {

    @Delegate
    private final Directional directionalDelegate;

    private final CapturedState capturedState;

    public BukkitCommandBlockWrapper(org.bukkit.block.Block bukkitCommandBlock) {
        directionalDelegate = new BukkitDirectionalWrapper(bukkitCommandBlock);
        capturedState = new SharedCapturedState(bukkitCommandBlock);
    }

    public static BukkitCommandBlockWrapper of(org.bukkit.block.Block bukkitCommandBlock) {
        if (bukkitCommandBlock == null) return null;
        return new BukkitCommandBlockWrapper(bukkitCommandBlock);
    }

    @Override
    public String getCommand() {
        return capturedState
                .<org.bukkit.block.CommandBlock>state()
                .getCommand();
    }
}
