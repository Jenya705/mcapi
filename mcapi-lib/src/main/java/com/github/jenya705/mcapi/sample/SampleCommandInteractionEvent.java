package com.github.jenya705.mcapi.sample;

import com.github.jenya705.mcapi.app.LibraryApplication;
import com.github.jenya705.mcapi.command.CommandInteractionValue;
import com.github.jenya705.mcapi.event.CommandInteractionEvent;

import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * This code will work if {@link SampleCreateCommand} executed.
 *
 * @see SampleCreateCommand
 * @author Jenya705
 */
class SampleCommandInteractionEvent {

    public static void main(String[] args) {
        LibraryApplication<?, ?> application = LibraryApplication.create("localhost", 8080, "ce727c0a74024afdbd6ed9d03225d4e60000001630142908370"); // some token
        application.start();
        application
                .tunnel()
                .on(CommandInteractionEvent.class, event -> {
                    if (event.getPath().equalsIgnoreCase("hello")) {
                        String name = Arrays
                                .stream(event.getValues())
                                .filter(it -> it.getName().equalsIgnoreCase("name"))
                                .findAny()
                                .map(CommandInteractionValue::getValue)
                                .map(it -> (String) it)
                                .orElseThrow(() -> new RuntimeException("Something happened"));
                        int count = Arrays
                                .stream(event.getValues())
                                .filter(it -> it.getName().equalsIgnoreCase("count"))
                                .findAny()
                                .map(CommandInteractionValue::getValue)
                                .map(it -> (int) it)
                                .orElse(1);
                        String builtString =
                                "Hello ".repeat(Math.max(0, count)) + "," + name;
                        event.getSender().sendMessage(builtString);
                    }
                });
        System.out.println("Started");
        new Scanner(System.in).nextLine();
    }
}
