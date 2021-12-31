package com.github.jenya705.mcapi.bukkit.block.data;

import com.github.jenya705.mcapi.bukkit.block.AbstractBukkitBlockData;
import com.github.jenya705.mcapi.bukkit.block.BukkitDirectionalWrapper;
import com.github.jenya705.mcapi.block.Directional;
import lombok.experimental.Delegate;
import org.bukkit.block.data.type.Piston;

/**
 * @author Jenya705
 */
public class BukkitPistonWrapper
        extends AbstractBukkitBlockData<Piston>
        implements com.github.jenya705.mcapi.block.data.Piston {

    @Delegate
    private final Directional directionalDelegate;

    public BukkitPistonWrapper(org.bukkit.block.Block block) {
        super(block);
        directionalDelegate = new BukkitDirectionalWrapper(block);
    }

    public static BukkitPistonWrapper of(org.bukkit.block.Block block) {
        if (block == null) return null;
        return new BukkitPistonWrapper(block);
    }

    @Override
    public boolean isExtended() {
        return data().isExtended();
    }

    @Override
    public void setExtended(boolean extended) {
        updateData(it -> it.setExtended(extended));
    }
}
