package com.github.jenya705.mcapi.block;

import com.github.jenya705.mcapi.BukkitWrapper;
import lombok.Getter;

/**
 * @author Jenya705
 */
@Getter
public class BukkitBisectedWrapper implements Bisected {

    private final Half half;

    public BukkitBisectedWrapper(org.bukkit.block.Block block) {
        half = BukkitWrapper.half((
                (org.bukkit.block.data.Bisected)
                        block.getBlockData())
                .getHalf());
    }
}
