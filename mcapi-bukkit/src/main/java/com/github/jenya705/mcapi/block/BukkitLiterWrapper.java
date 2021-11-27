package com.github.jenya705.mcapi.block;

import com.github.jenya705.mcapi.BukkitUtils;
import lombok.AllArgsConstructor;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class BukkitLiterWrapper implements Liter {

    private final org.bukkit.block.Block block;

    @Override
    public boolean isLit() {
        return data().isLit();
    }

    @Override
    public void setLit(boolean lit) {
        org.bukkit.block.data.Lightable data = data();
        data.setLit(lit);
        BukkitUtils.notAsyncTask(() -> block.setBlockData(data));
    }

    private org.bukkit.block.data.Lightable data() {
        return (org.bukkit.block.data.Lightable) block.getBlockData();
    }

}
