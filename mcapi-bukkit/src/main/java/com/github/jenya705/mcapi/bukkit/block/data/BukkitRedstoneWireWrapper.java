package com.github.jenya705.mcapi.bukkit.block.data;

import com.github.jenya705.mcapi.block.BlockFace;
import com.github.jenya705.mcapi.block.LevelPowerable;
import com.github.jenya705.mcapi.bukkit.wrapper.BukkitWrapper;
import com.github.jenya705.mcapi.bukkit.block.AbstractBukkitBlockData;
import com.github.jenya705.mcapi.bukkit.block.BukkitLevelPowerableWrapper;
import lombok.experimental.Delegate;
import org.bukkit.block.data.type.RedstoneWire;

/**
 * @author Jenya705
 */
public class BukkitRedstoneWireWrapper
        extends AbstractBukkitBlockData<RedstoneWire>
        implements com.github.jenya705.mcapi.block.data.RedstoneWire {

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
