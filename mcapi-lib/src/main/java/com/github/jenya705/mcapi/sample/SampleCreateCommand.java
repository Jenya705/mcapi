package com.github.jenya705.mcapi.sample;

import com.github.jenya705.mcapi.app.DefaultLibraryApplication;
import com.github.jenya705.mcapi.app.LibraryApplication;
import com.github.jenya705.mcapi.builder.command.CommandBuilder;
import com.github.jenya705.mcapi.builder.command.option.CommandValueOptionBuilder;

/**
 * @author Jenya705
 */
class SampleCreateCommand {

    public static void main(String[] args) {
        LibraryApplication<?, ?> application = LibraryApplication.create("localhost", 8080, "ce727c0a74024afdbd6ed9d03225d4e60000001630142908370"); // some token
        application.start();
        application
                .rest()
                .createCommand(CommandBuilder
                        .create()
                        .name("hello")
                        .option(CommandValueOptionBuilder
                                .string()
                                .name("name")
                                .required()
                                .suggestion("Jenya705")
                                .build()
                        )
                        .option(CommandValueOptionBuilder
                                .integer()
                                .name("count")
                                .min(0)
                                .max(5)
                                .build()
                        )
                        .build()
                )
                .block();
        application
                .rest()
                .stop()
                .block();
    }
}
