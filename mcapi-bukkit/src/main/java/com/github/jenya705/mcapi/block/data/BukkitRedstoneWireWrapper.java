package com.github.jenya705.mcapi.block.data;

import com.github.jenya705.mcapi.BukkitWrapper;
import com.github.jenya705.mcapi.block.AbstractBukkitBlockData;
import com.github.jenya705.mcapi.block.BlockFace;
import com.github.jenya705.mcapi.block.BukkitLevelPowerableWrapper;
import com.github.jenya705.mcapi.block.LevelPowerable;
import lombok.experimental.Delegate;

/**
 * @author Jenya705
 */
public class BukkitRedstoneWireWrapper
        extends AbstractBukkitBlockData<org.bukkit.block.data.type.RedstoneWire>
        implements RedstoneWire {

    @Delegate
    private final LevelPowerable levelPowerableDelegate;

    public BukkitRedstoneWireWrapper(org.bukkit.block.Block block) {
        super(block);
        levelPowerableDelegate = new BukkitLevelPowerableWrapper(block);
    }

    public static BukkitRedstoneWireWrapper of(org.bukkit.block.Block block) {
        if (block == null) return null;
        return new BukkitRedstoneWireWrapper(block);
    }

    @Override
    public Connection getConnection(BlockFace face) {
        return BukkitWrapper.connection(
                data().getFace(BukkitWrapper.blockFace(face))
        );
    }

    @Override
    public void setConnection(BlockFace face, Connection connection) {
        updateData(it -> it.setFace(
                BukkitWrapper.blockFace(face),
                BukkitWrapper.connection(connection)
        ));
    }
}
