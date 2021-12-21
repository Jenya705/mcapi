package com.github.jenya705.mcapi.menu;

import com.github.jenya705.mcapi.inventory.ItemStack;

/**
 * @author Jenya705
 */
public interface MenuItem extends ItemStack {

    MenuCallback getCallback();

}
