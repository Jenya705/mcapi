package com.github.jenya705.mcapi.block;

import lombok.AllArgsConstructor;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class BukkitWaterloggedWrapper implements Waterlogged {

    private final org.bukkit.block.Block block;

    @Override
    public boolean isWaterlogged() {
        return data().isWaterlogged();
    }

    @Override
    public void setWaterlogged(boolean waterlogged) {
        data().setWaterlogged(waterlogged);
    }

    private org.bukkit.block.data.Waterlogged data() {
        return (org.bukkit.block.data.Waterlogged) block.getBlockData();
    }

}
