package com.github.jenya705.mcapi.bukkit.inventory;

import com.github.jenya705.mcapi.server.util.Pair;
import lombok.experimental.UtilityClass;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jenya705
 */
@UtilityClass
public class BukkitInventoryUtils {

    public int getItemSpace(Inventory inventory, ItemStack forItem) {
        int result = 0;
        for (ItemStack item : inventory.getStorageContents()) {
            if (item == null || item.getType().isAir()) {
                result += forItem.getMaxStackSize();
            }
            else if (item.getType().equals(forItem.getType())) {
                result += forItem.getMaxStackSize() - item.getAmount();
            }
        }
        return result;
    }

    public List<Pair<Integer, Integer>> getPossibleItemIndexes(Inventory inventory, ItemStack forItem) {
        List<Pair<Integer, Integer>> result = new ArrayList<>();
        int amount = forItem.getAmount();
        int currentIndex = 0;
        for (ItemStack item : inventory.getStorageContents()) {
            if (item == null || item.getType().isAir()) {
                result.add(new Pair<>(currentIndex, amount));
                break;
            }
            else if (item.getType().equals(forItem.getType())) {
                int canAdd = forItem.getMaxStackSize() - item.getAmount();
                if (canAdd >= amount) {
                    result.add(new Pair<>(currentIndex, amount));
                    break;
                }
                else if (canAdd != 0) {
                    amount -= canAdd;
                    result.add(new Pair<>(currentIndex, canAdd));
                }
            }
            currentIndex++;
        }
        return result;
    }

}
