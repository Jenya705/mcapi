package com.github.jenya705.mcapi.bukkit.block.data;

import com.github.jenya705.mcapi.block.Directional;
import com.github.jenya705.mcapi.block.data.CommandBlock;
import com.github.jenya705.mcapi.bukkit.block.BukkitDirectionalWrapper;
import com.github.jenya705.mcapi.bukkit.block.BukkitStateContainer;
import com.github.jenya705.mcapi.bukkit.block.state.CapturedState;
import com.github.jenya705.mcapi.bukkit.block.state.SharedCapturedState;
import lombok.Getter;
import lombok.experimental.Delegate;

/**
 * @author Jenya705
 */
public class BukkitCommandBlockWrapper implements CommandBlock, BukkitStateContainer {

    @Delegate
    private final Directional directionalDelegate;
    @Getter
    private final CapturedState state;

    public BukkitCommandBlockWrapper(org.bukkit.block.Block bukkitCommandBlock) {
        directionalDelegate = new BukkitDirectionalWrapper(bukkitCommandBlock);
        state = new SharedCapturedState(bukkitCommandBlock);
    }

    public static BukkitCommandBlockWrapper of(org.bukkit.block.Block bukkitCommandBlock) {
        if (bukkitCommandBlock == null) return null;
        return new BukkitCommandBlockWrapper(bukkitCommandBlock);
    }

    @Override
    public String getCommand() {
        return state
                .<org.bukkit.block.CommandBlock>state()
                .getCommand();
    }
}
