package com.github.jenya705.mcapi.block.data;

import com.github.jenya705.mcapi.BukkitWrapper;
import com.github.jenya705.mcapi.block.*;
import lombok.Getter;
import lombok.experimental.Delegate;

/**
 * @author Jenya705
 */
public class BukkitStairsWrapper implements Stairs {

    @Delegate
    private final Bisected bisectedDelegate;
    @Delegate
    private final Directional directionalDelegate;
    @Delegate
    private final Waterlogged waterloggedDelegate;
    @Getter
    private final Shape shape;

    public BukkitStairsWrapper(org.bukkit.block.Block block) {
        bisectedDelegate = new BukkitBisectedWrapper(block);
        directionalDelegate = new BukkitDirectionalWrapper(block);
        waterloggedDelegate = new BukkitWaterloggedWrapper(block);
        shape = BukkitWrapper.shape(
                ((org.bukkit.block.data.type.Stairs)
                        block.getBlockData())
                        .getShape()
        );
    }

    public static BukkitStairsWrapper of(org.bukkit.block.Block block) {
        if (block == null) return null;
        return new BukkitStairsWrapper(block);
    }
}
