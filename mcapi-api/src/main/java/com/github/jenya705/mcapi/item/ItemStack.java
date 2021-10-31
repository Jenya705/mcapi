package com.github.jenya705.mcapi.item;

import com.github.jenya705.mcapi.Material;

/**
 * @author Jenya705
 */
public interface ItemStack {

    Material getMaterial();

    int getAmount();

    String getName();

}
