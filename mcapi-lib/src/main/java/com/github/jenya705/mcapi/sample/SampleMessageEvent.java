package com.github.jenya705.mcapi.sample;

import com.github.jenya705.mcapi.app.LibraryApplication;
import com.github.jenya705.mcapi.event.MessageEvent;

import java.util.Scanner;

/**
 * @author Jenya705
 */
class SampleMessageEvent {

    public static void main(String[] args) {
        LibraryApplication<?, ?> application = LibraryApplication.create("localhost", 8080, "ce727c0a74024afdbd6ed9d03225d4e60000001630142908370"); // some token
        application.start();
        application
                .tunnel()
                .on(MessageEvent.class, event -> event
                        .getAuthor()
                        .sendMessage(String.format(
                                "You typed %s", event.getMessage()
                        ))
                );
        new Scanner(System.in).nextLine();
    }

}
