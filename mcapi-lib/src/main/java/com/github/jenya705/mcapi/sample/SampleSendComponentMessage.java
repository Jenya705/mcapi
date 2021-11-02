package com.github.jenya705.mcapi.sample;

import com.github.jenya705.mcapi.JavaRestClient;
import com.github.jenya705.mcapi.TunnelClient;
import com.github.jenya705.mcapi.app.LibraryApplication;
import com.github.jenya705.mcapi.message.ComponentMessage;
import com.github.jenya705.mcapi.selector.PlayerSelector;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;

/**
 * @author Jenya705
 */
class SampleSendComponentMessage {

    public static void main(String[] args) {
        LibraryApplication<JavaRestClient, TunnelClient> application =
                LibraryApplication.java("localhost", 8080, "ce727c0a74024afdbd6ed9d03225d4e60000001630142908370"); // some token
        application.start();
        application
                .rest()
                .sendMessage(PlayerSelector.all, new ComponentMessage(
                        Component
                                .text("Hi!")
                                .color(NamedTextColor.GRAY)
                                .append(Component
                                        .text(" and bye!")
                                        .color(NamedTextColor.RED)
                                        .clickEvent(ClickEvent.runCommand("bye!"))
                                )
                ))
                .block();
        application.stop();
    }
}
