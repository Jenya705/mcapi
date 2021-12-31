package com.github.jenya705.mcapi.bukkit.block.data;

import com.github.jenya705.mcapi.bukkit.BukkitWrapper;
import com.github.jenya705.mcapi.bukkit.block.AbstractBukkitBlockData;
import com.github.jenya705.mcapi.bukkit.block.BukkitBisectedWrapper;
import com.github.jenya705.mcapi.bukkit.block.BukkitDirectionalWrapper;
import com.github.jenya705.mcapi.bukkit.block.BukkitWaterloggedWrapper;
import com.github.jenya705.mcapi.block.*;
import lombok.experimental.Delegate;
import org.bukkit.block.data.type.Stairs;

/**
 * @author Jenya705
 */
public class BukkitStairsWrapper
        extends AbstractBukkitBlockData<Stairs>
        implements com.github.jenya705.mcapi.block.data.Stairs {

    @Delegate
    private final Bisected bisectedDelegate;
    @Delegate
    private final Directional directionalDelegate;
    @Delegate
    private final Waterlogged waterloggedDelegate;

    public BukkitStairsWrapper(org.bukkit.block.Block block) {
        super(block);
        bisectedDelegate = new BukkitBisectedWrapper(block);
        directionalDelegate = new BukkitDirectionalWrapper(block);
        waterloggedDelegate = new BukkitWaterloggedWrapper(block);
    }

    public Shape getShape() {
        return BukkitWrapper.shape(data().getShape());
    }

    @Override
    public void setShape(Shape shape) {
        updateData(it -> it.setShape(BukkitWrapper.shape(shape)));
    }

    public static BukkitStairsWrapper of(org.bukkit.block.Block block) {
        if (block == null) return null;
        return new BukkitStairsWrapper(block);
    }
}
