package com.github.jenya705.mcapi.block.data;

import com.github.jenya705.mcapi.BukkitWrapper;
import com.github.jenya705.mcapi.block.*;
import lombok.experimental.Delegate;

/**
 * @author Jenya705
 */
public class BukkitDoorWrapper
    extends AbstractBukkitBlockData<org.bukkit.block.data.type.Door>
    implements Door
{

    @Delegate
    private final Openable openableDelegate;
    @Delegate
    private final Bisected bisectedDelegate;
    @Delegate
    private final Directional directionalDelegate;
    @Delegate
    private final Powerable powerableDelegate;

    public BukkitDoorWrapper(org.bukkit.block.Block block) {
        super(block);
        openableDelegate = new BukkitOpenableWrapper(block);
        bisectedDelegate = new BukkitBisectedWrapper(block);
        directionalDelegate = new BukkitDirectionalWrapper(block);
        powerableDelegate = new BukkitPowerableWrapper(block);
    }

    public static BukkitDoorWrapper of(org.bukkit.block.Block block) {
        if (block == null) return null;
        return new BukkitDoorWrapper(block);
    }

    @Override
    public Hinge getHinge() {
        return BukkitWrapper.hinge(data().getHinge());
    }

    @Override
    public void setHinge(Hinge hinge) {
        updateData(it -> it.setHinge(BukkitWrapper.hinge(hinge)));
    }
}
