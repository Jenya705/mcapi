package com.github.jenya705.mcapi.sample;

import com.github.jenya705.mcapi.JavaRestClient;
import com.github.jenya705.mcapi.PlayerID;
import com.github.jenya705.mcapi.TunnelClient;
import com.github.jenya705.mcapi.app.LibraryApplication;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

/**
 * @author Jenya705
 */
class SampleGetJavaPlayer {

    public static void main(String[] args) throws InterruptedException {
        LibraryApplication<JavaRestClient, TunnelClient> application =
                LibraryApplication.java("localhost", 8080, "ce727c0a74024afdbd6ed9d03225d4e60000001630142908370"); // some token
        application.start();
        application
                .rest()
                .getPlayer(PlayerID.of("Jenya705"))
                .block()
                .sendMessage(
                        Component
                                .text("Hi!")
                                .color(NamedTextColor.GREEN)
                                .append(Component
                                        .text(" Bye!")
                                        .color(NamedTextColor.RED)
                                )
                );
        Thread.sleep(5000); // wait for sending message
    }
}
