package com.github.jenya705.mcapi.sample;

import com.github.jenya705.mcapi.Player;
import com.github.jenya705.mcapi.app.DefaultLibraryApplication;
import com.github.jenya705.mcapi.app.LibraryApplication;

/**
 * @author Jenya705
 */
class SampleGetPlayers {

    public static void main(String[] args) {
        LibraryApplication<?, ?> application = LibraryApplication.create("localhost", 8080, "ce727c0a74024afdbd6ed9d03225d4e60000001630142908370"); // some token
        application.start();
        application
                .rest()
                .getOnlinePlayers()
                .map(Player::getName)
                .collectList()
                .block()
                .forEach(System.out::println);
        application
                .rest()
                .stop()
                .block();
    }

}