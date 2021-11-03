package com.github.jenya705.mcapi.sample;

import com.github.jenya705.mcapi.app.LibraryApplication;
import com.github.jenya705.mcapi.world.World;

/**
 * @author Jenya705
 */
class SampleGetWorld {

    public static void main(String[] args) {
        LibraryApplication<?, ?> application = LibraryApplication.create("localhost", 8080, "ce727c0a74024afdbd6ed9d03225d4e60000001630142908370"); // some token
        application.start();
        World world = application
                .rest()
                .getWorld("overworld")
                .block();
        if (world != null) {
            System.out.printf(
                    "World dimension is %s, weather conditions is %s",
                    world.getWorldDimension().name(),
                    world.getWorldWeather().name()
            );
        }
        application.stop();
    }

}
