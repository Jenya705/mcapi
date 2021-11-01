package com.github.jenya705.mcapi.sample;

import com.github.jenya705.mcapi.PlayerID;
import com.github.jenya705.mcapi.app.DefaultLibraryApplication;
import com.github.jenya705.mcapi.app.LibraryApplication;
import com.github.jenya705.mcapi.inventory.ItemStack;

/**
 * @author Jenya705
 */
class SampleGetPlayerInventoryItem {

    public static void main(String[] args) {
        LibraryApplication application = new DefaultLibraryApplication("localhost", 8080, "ce727c0a74024afdbd6ed9d03225d4e60000001630142908370"); // some token
        application.start();
        ItemStack itemStack = application
                .rest()
                .getPlayerInventoryItem(PlayerID.of("Jenya705"), 0, 0)
                .block();
        if (itemStack != null) {
            System.out.printf(
                    "Item in (0, 0) is %s with amount of %s and custom name %s",
                    itemStack.getMaterial().getKey(),
                    itemStack.getAmount(),
                    itemStack.getCustomName()
            );
        }
        application.stop();
    }

}
