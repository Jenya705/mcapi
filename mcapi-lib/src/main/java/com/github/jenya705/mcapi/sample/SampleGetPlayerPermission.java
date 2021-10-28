package com.github.jenya705.mcapi.sample;

import com.github.jenya705.mcapi.Permission;
import com.github.jenya705.mcapi.PlayerID;
import com.github.jenya705.mcapi.app.DefaultLibraryApplication;
import com.github.jenya705.mcapi.app.LibraryApplication;

/**
 * @author Jenya705
 */
class SampleGetPlayerPermission {

    public static void main(String[] args) {
        LibraryApplication application = new DefaultLibraryApplication("localhost", 8080, "ce727c0a74024afdbd6ed9d03225d4e60000001630142908370"); // some token
        Permission permission =
                application
                        .rest()
                        .getPlayerPermission(
                                PlayerID.of("Jenya705"),
                                "mcapi.command"
                        )
                        .block();
        if (permission != null) {
            System.out.printf(
                    "Is this permission toggled: %s",
                    permission.isToggled()
            );
        }
        application
                .rest()
                .stop()
                .block();
    }
}
