package com.github.jenya705.mcapi.sample;

import com.github.jenya705.mcapi.PlayerID;
import com.github.jenya705.mcapi.app.LibraryApplication;
import com.github.jenya705.mcapi.builder.link.LinkRequestBuilder;
import com.github.jenya705.mcapi.permission.Permissions;

/**
 * @author Jenya705
 */
class SampleRequestLink {

    public static void main(String[] args) {
        LibraryApplication<?, ?> application = LibraryApplication.create("localhost", 8080, "ce727c0a74024afdbd6ed9d03225d4e60000001630142908370"); // some token
        application.start();
        application
                .rest()
                .requestLink(
                        PlayerID.of("Jenya705"),
                        LinkRequestBuilder
                                .create()
                                .requirePermission(Permissions.USER_GET)
                                .requirePermission(Permissions.USER_GET_LOCATION)
                                .optionalPermission(Permissions.USER_KICK)
                                .command("hello")
                                .build()
                )
                .block();
        application
                .rest()
                .stop()
                .block();
    }
}
