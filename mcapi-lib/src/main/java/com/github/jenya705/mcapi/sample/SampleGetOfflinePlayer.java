package com.github.jenya705.mcapi.sample;

import com.github.jenya705.mcapi.OfflinePlayer;
import com.github.jenya705.mcapi.PlayerID;
import com.github.jenya705.mcapi.app.LibraryApplication;

/**
 * @author Jenya705
 */
class SampleGetOfflinePlayer {

    public static void main(String[] args) {
        LibraryApplication<?, ?> application = LibraryApplication.create("localhost", 8080, "ce727c0a74024afdbd6ed9d03225d4e60000001630142908370"); // some token
        application.start();
        OfflinePlayer player = application
                .rest()
                .getOfflinePlayer(PlayerID.of("Jenya705"))
                .block();
        if (player != null) {
            System.out.printf("UUID of the player %s", player.getUuid());
        }
        application
                .rest()
                .stop()
                .block();
    }

}
