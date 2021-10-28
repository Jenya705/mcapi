package com.github.jenya705.mcapi.sample;

import com.github.jenya705.mcapi.Player;
import com.github.jenya705.mcapi.PlayerID;
import com.github.jenya705.mcapi.app.DefaultLibraryApplication;
import com.github.jenya705.mcapi.app.LibraryApplication;

/**
 * @author Jenya705
 */
class SampleGetPlayer {

    public static void main(String[] args) {
        LibraryApplication application = new DefaultLibraryApplication("localhost", 8080, "ce727c0a74024afdbd6ed9d03225d4e60000001630142908370"); // some token
        Player player = application
                .rest()
                .getPlayer(PlayerID.of("Jenya705"))
                .block();
        if (player == null) {
            System.out.println("Player is null!");
            return;
        }
        System.out.printf(
                "I found the player with name %s and uuid %s%n",
                player.getName(), player.getUuid()
        );
        application
                .rest()
                .stop()
                .block();
    }

}
