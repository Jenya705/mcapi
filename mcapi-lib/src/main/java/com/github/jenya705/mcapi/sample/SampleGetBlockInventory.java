package com.github.jenya705.mcapi.sample;

import com.github.jenya705.mcapi.app.DefaultLibraryApplication;
import com.github.jenya705.mcapi.app.LibraryApplication;
import com.github.jenya705.mcapi.inventory.Inventory;
import com.github.jenya705.mcapi.inventory.ItemStack;

/**
 * @author Jenya705
 */
class SampleGetBlockInventory {

    public static void main(String[] args) {
        LibraryApplication application = new DefaultLibraryApplication("localhost", 8080, "ce727c0a74024afdbd6ed9d03225d4e60000001630142908370"); // some token
        application.start();
        Inventory blockInventory = application
                .rest()
                .getBlockInventory("overworld", 0, 100, 0)
                .block();
        if (blockInventory != null) {
            for (ItemStack itemStack: blockInventory.getAllItems()) {
                System.out.printf(
                        "Another item is %s with amount of %s and custom name %s\n",
                        itemStack.getMaterial().getKey(),
                        itemStack.getAmount(),
                        itemStack.getCustomName()
                );
            }
        }
    }
}
