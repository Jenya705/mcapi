package com.github.jenya705.mcapi.block;

import lombok.AllArgsConstructor;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class BukkitCommandBlockWrapper implements CommandBlock {

    private final org.bukkit.block.CommandBlock bukkitCommandBlock;

    public static BukkitCommandBlockWrapper of(org.bukkit.block.CommandBlock bukkitCommandBlock) {
        if (bukkitCommandBlock == null) return null;
        return new BukkitCommandBlockWrapper(bukkitCommandBlock);
    }

    @Override
    public String getCommand() {
        return bukkitCommandBlock.getCommand();
    }
}
