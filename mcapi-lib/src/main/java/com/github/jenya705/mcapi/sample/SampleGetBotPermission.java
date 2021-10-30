package com.github.jenya705.mcapi.sample;

import com.github.jenya705.mcapi.Permission;
import com.github.jenya705.mcapi.app.DefaultLibraryApplication;
import com.github.jenya705.mcapi.app.LibraryApplication;
import com.github.jenya705.mcapi.permission.Permissions;
import com.github.jenya705.mcapi.selector.BotSelector;

/**
 * @author Jenya705
 */
class SampleGetBotPermission {

    public static void main(String[] args) {
        LibraryApplication application = new DefaultLibraryApplication("localhost", 8080, "ce727c0a74024afdbd6ed9d03225d4e60000001630142908370"); // some token
        application.start();
        Permission permission = application
                .rest()
                .getBotPermission(
                        BotSelector.me,
                        Permissions.USER_KICK // or just "user.kick"
                )
                .block();
        if (permission != null) {
            System.out.printf("Can i kick players: %s", permission.isToggled());
        }
        application
                .rest()
                .stop()
                .block();
    }
}
