package com.github.jenya705.mcapi.sample;

import com.github.jenya705.mcapi.app.DefaultLibraryApplication;
import com.github.jenya705.mcapi.app.LibraryApplication;
import com.github.jenya705.mcapi.event.JoinEvent;
import com.github.jenya705.mcapi.event.QuitEvent;

import java.util.Scanner;

/**
 * @author Jenya705
 */
class SampleJoinQuitEvent {

    public static void main(String[] args) {
        LibraryApplication<?, ?> application = LibraryApplication.create("localhost", 8080, "ce727c0a74024afdbd6ed9d03225d4e60000001630142908370"); // some token
        application.start();
        application
                .tunnel()
                .on(JoinEvent.class, event -> System.out.printf("Player %s joined!", event.getPlayer().getName()));
        application
                .tunnel()
                .on(QuitEvent.class, event -> System.out.printf("Player %s left!", event.getPlayer().getName()));
        new Scanner(System.in).nextLine();
    }

}
