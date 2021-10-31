package com.github.jenya705.mcapi.sample;

import com.github.jenya705.mcapi.app.DefaultLibraryApplication;
import com.github.jenya705.mcapi.app.LibraryApplication;
import com.github.jenya705.mcapi.block.Block;

/**
 * @author Jenya705
 */
class SampleGetBlock {

    public static void main(String[] args) {
        LibraryApplication application = new DefaultLibraryApplication("localhost", 8080, "ce727c0a74024afdbd6ed9d03225d4e60000001630142908370"); // some token
        application.start();
        Block block = application
                .rest()
                .getBlock("overworld", 0, 0, 0)
                .block();
        if (block != null) {
            System.out.printf("Block type is: %s", block.getMaterial().getKey());
        }
        application.stop();
    }
}
