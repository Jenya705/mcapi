package com.github.jenya705.mcapi.block;

import com.github.jenya705.mcapi.BukkitWrapper;
import com.github.jenya705.mcapi.block.data.Directional;
import lombok.Getter;

/**
 * @author Jenya705
 */
@Getter
public class BukkitDirectionalWrapper implements Directional {

    private final BlockFace direction;

    public BukkitDirectionalWrapper(org.bukkit.block.Block block) {
        direction = BukkitWrapper.blockFace(
                ((org.bukkit.block.data.Directional)
                        block.getBlockData())
                        .getFacing()
        );
    }
}
