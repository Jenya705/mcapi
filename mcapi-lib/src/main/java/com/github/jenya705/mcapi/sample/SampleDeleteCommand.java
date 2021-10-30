package com.github.jenya705.mcapi.sample;

import com.github.jenya705.mcapi.app.DefaultLibraryApplication;
import com.github.jenya705.mcapi.app.LibraryApplication;

/**
 * @author Jenya705
 */
class SampleDeleteCommand {

    public static void main(String[] args) {
        LibraryApplication application = new DefaultLibraryApplication("localhost", 8080, "ce727c0a74024afdbd6ed9d03225d4e60000001630142908370"); // some token
        application
                .rest()
                .deleteCommand("hello")
                .block();
        application
                .rest()
                .stop()
                .block();
    }

}
