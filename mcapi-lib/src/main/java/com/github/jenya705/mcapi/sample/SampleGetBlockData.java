package com.github.jenya705.mcapi.sample;

import com.github.jenya705.mcapi.VanillaMaterial;
import com.github.jenya705.mcapi.app.DefaultLibraryApplication;
import com.github.jenya705.mcapi.app.LibraryApplication;
import com.github.jenya705.mcapi.block.CommandBlock;

/**
 * @author Jenya705
 */
class SampleGetBlockData {

    public static void main(String[] args) {
        LibraryApplication application = new DefaultLibraryApplication("localhost", 8080, "ce727c0a74024afdbd6ed9d03225d4e60000001630142908370"); // some token
        application.start();
        CommandBlock commandBlock = (CommandBlock)
                application
                        .rest()
                        .getBlockData(
                                "overworld",
                                0, 50, 0,
                                VanillaMaterial.COMMAND_BLOCK.getKey()
                        )
                        .block();
        if (commandBlock != null) {
            System.out.printf("Command typed in command block is %s", commandBlock.getCommand());
        }
        application
                .rest()
                .stop()
                .block();
    }
}
