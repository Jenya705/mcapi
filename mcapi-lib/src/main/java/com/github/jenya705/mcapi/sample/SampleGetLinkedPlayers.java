package com.github.jenya705.mcapi.sample;

import com.github.jenya705.mcapi.OfflinePlayer;
import com.github.jenya705.mcapi.app.LibraryApplication;
import com.github.jenya705.mcapi.selector.BotSelector;

/**
 * @author Jenya705
 */
class SampleGetLinkedPlayers {

    public static void main(String[] args) {
        LibraryApplication<?, ?> application = LibraryApplication.create("localhost", 8080, "ce727c0a74024afdbd6ed9d03225d4e60000001630142908370"); // some token
        application.start();
        System.out.println(
                application
                        .rest()
                        .getLinkedPlayers(BotSelector.me)
                        .map(OfflinePlayer::getName)
                        .collectList()
                        .block()
        );
        application
                .rest()
                .stop()
                .block();
    }
}
