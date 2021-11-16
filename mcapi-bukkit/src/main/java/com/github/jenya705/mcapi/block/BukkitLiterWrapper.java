package com.github.jenya705.mcapi.block;

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
        data().setLit(lit);
    }

    private org.bukkit.block.data.Lightable data() {
        return (org.bukkit.block.data.Lightable) block.getBlockData();
    }

}
