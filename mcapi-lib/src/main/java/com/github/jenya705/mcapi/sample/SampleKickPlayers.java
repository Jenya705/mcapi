package com.github.jenya705.mcapi.sample;

import com.github.jenya705.mcapi.app.LibraryApplication;
import com.github.jenya705.mcapi.message.DefaultMessage;
import com.github.jenya705.mcapi.selector.PlayerSelector;

/**
 * @author Jenya705
 */
class SampleKickPlayers {

    public static void main(String[] args) {
        LibraryApplication<?, ?> application = LibraryApplication.create("localhost", 8080, "ce727c0a74024afdbd6ed9d03225d4e60000001630142908370"); // some token
        application.start();
        application
                .rest()
                .kickPlayers(
                        PlayerSelector.of("Jenya705"),
                        new DefaultMessage("Bad boy")
                )
                .block();
        application
                .rest()
                .stop()
                .block();
    }

}
