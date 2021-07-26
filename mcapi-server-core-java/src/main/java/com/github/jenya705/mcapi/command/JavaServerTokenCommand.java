package com.github.jenya705.mcapi.command;

import com.github.jenya705.mcapi.ApiSender;
import com.github.jenya705.mcapi.ApiServerApplication;
import com.github.jenya705.mcapi.JavaServerConfiguration;
import lombok.experimental.UtilityClass;

/**
 * @since 1.0
 * @author Jenya705
 */
@UtilityClass
public class JavaServerTokenCommand {

    protected static JavaServerConfiguration getConfig() {
        return (JavaServerConfiguration) ApiServerApplication.getApplication().getCore().getConfig();
    }

    public static void sendMessagePlayerNameIsNotGiven(ApiSender sender) {
        sender.sendMessage(getConfig().getTokenPlayerNameIsNotGivenMessage());
    }

    public static void sendMessagePlayerIsNotExist(ApiSender sender) {
        sender.sendMessage(getConfig().getTokenPlayerIsNotExistMessage());
    }

}
