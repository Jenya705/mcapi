package com.github.jenya705.mcapi.bukkit.block;

import com.github.jenya705.mcapi.block.Openable;

/**
 * @author Jenya705
 */
public class BukkitOpenableWrapper
    extends AbstractBukkitBlockData<org.bukkit.block.data.Openable>
    implements Openable
{

    public BukkitOpenableWrapper(org.bukkit.block.Block block) {
        super(block);
    }

    @Override
    public boolean isOpen() {
        return data().isOpen();
    }

    @Override
    public void setOpen(boolean opened) {
        updateData(it -> it.setOpen(opened));
    }
}
