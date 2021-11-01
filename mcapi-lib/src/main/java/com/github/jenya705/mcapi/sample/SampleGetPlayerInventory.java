package com.github.jenya705.mcapi.sample;

import com.github.jenya705.mcapi.PlayerID;
import com.github.jenya705.mcapi.app.DefaultLibraryApplication;
import com.github.jenya705.mcapi.app.LibraryApplication;
import com.github.jenya705.mcapi.inventory.PlayerInventory;

/**
 * @author Jenya705
 */
class SampleGetPlayerInventory {

    public static void main(String[] args) {
        LibraryApplication application = new DefaultLibraryApplication("localhost", 8080, "ce727c0a74024afdbd6ed9d03225d4e60000001630142908370"); // some token
        application.start();
        PlayerInventory playerInventory = application
                .rest()
                .getPlayerInventory(PlayerID.of("Jenya705"))
                .block();
        if (playerInventory != null) {
            System.out.printf("Helmet is %s\n", playerInventory.getHelmet().getMaterial());
            System.out.printf("Chestplate is %s\n", playerInventory.getChestplate().getMaterial());
            System.out.printf("Leggings is %s\n", playerInventory.getLeggings().getMaterial());
            System.out.printf("Boots is %s\n", playerInventory.getBoots().getMaterial());
            System.out.printf("Main hand is %s\n", playerInventory.getMainHand().getMaterial());
            System.out.printf("Off hand is %s\n", playerInventory.getOffHand().getMaterial());
        }
        application.stop();
    }

}
