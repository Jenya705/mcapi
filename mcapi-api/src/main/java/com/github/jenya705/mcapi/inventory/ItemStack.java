package com.github.jenya705.mcapi.inventory;

import com.github.jenya705.mcapi.Material;
import com.github.jenya705.mcapi.enchantment.ItemEnchantment;
import net.kyori.adventure.text.Component;

import java.util.List;

/**
 * @author Jenya705
 */
public interface ItemStack {

    Material getMaterial();

    int getAmount();

    String getCustomName();

    Component customName();

    List<ItemEnchantment> getEnchantments();

}
