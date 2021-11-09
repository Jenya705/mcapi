package com.github.jenya705.mcapi.inventory;

import com.github.jenya705.mcapi.Material;
import net.kyori.adventure.text.Component;

/**
 * @author Jenya705
 */
public interface ItemStack {

    Material getMaterial();

    int getAmount();

    String getCustomName();

    Component customName();

}
