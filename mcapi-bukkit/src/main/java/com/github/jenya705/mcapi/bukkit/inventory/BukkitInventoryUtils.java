package com.github.jenya705.mcapi.bukkit.inventory;

import com.github.jenya705.mcapi.Material;
import lombok.experimental.UtilityClass;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * @author Jenya705
 */
@UtilityClass
public class BukkitInventoryUtils {

    public int getItemSpace(Inventory inventory, ItemStack forItem) {
        int result = 0;
        for (ItemStack item: inventory.getStorageContents()) {
            if (item == null || item.getType().isAir()) {
                result += forItem.getMaxStackSize();
            }
            else if (item.getType().equals(forItem.getType())) {
                result += forItem.getMaxStackSize() - item.getAmount();
            }
        }
        return result;
    }

}
