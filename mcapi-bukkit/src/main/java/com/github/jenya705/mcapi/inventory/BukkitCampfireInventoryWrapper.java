package com.github.jenya705.mcapi.inventory;

import com.github.jenya705.mcapi.BukkitWrapper;
import com.github.jenya705.mcapi.block.state.CapturedState;
import lombok.AllArgsConstructor;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class BukkitCampfireInventoryWrapper implements Inventory {

    private final CapturedState state;

    @Override
    public int getSize() {
        return state().getSize();
    }

    @Override
    public ItemStack[] getAllItems() {
        return new ItemStack[]{
                getItem(0),
                getItem(1),
                getItem(2),
                getItem(3)
        };
    }

    @Override
    public ItemStack getItem(int item) {
        return BukkitWrapper.itemStack(state().getItem(item));
    }

    @Override
    public void setItem(int item, ItemStack itemStack) {
        state().setItem(item, BukkitWrapper.itemStack(itemStack));
    }

    private org.bukkit.block.Campfire state() {
        return state.state();
    }

}
