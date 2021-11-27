package com.github.jenya705.mcapi.block;

import com.github.jenya705.mcapi.BukkitUtils;
import lombok.AllArgsConstructor;
import org.bukkit.Material;

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
        org.bukkit.block.data.Waterlogged data = data();
        data.setWaterlogged(waterlogged);
        BukkitUtils.notAsyncTask(() -> block.setBlockData(data));
    }

    private org.bukkit.block.data.Waterlogged data() {
        return (org.bukkit.block.data.Waterlogged) block.getBlockData();
    }

}
