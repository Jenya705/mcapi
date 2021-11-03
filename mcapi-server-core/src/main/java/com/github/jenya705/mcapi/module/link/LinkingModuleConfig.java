package com.github.jenya705.mcapi.module.link;

import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.data.loadable.Global;
import com.github.jenya705.mcapi.data.loadable.Value;
import com.github.jenya705.mcapi.module.config.Config;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

/**
 * @author Jenya705
 */
@Getter
@Setter
@ToString
public class LinkingModuleConfig extends Config {

    @Value
    private String title = "&eLinking with %bot_name%";

    @Value
    private String contentLayout = "&e%bot_name% want this permissions\n%list%";

    @Value
    @Global("contentRequiredListElementRepresentation")
    private String contentRequiredElement = "&7- &e%permission%";

    @Value
    @Global("listsDelimiter")
    private String contentDelimiter = "&r\n";

    @Value
    @Global("contentOptionalListElementRepresentation")
    private String contentOptionalElement = "&7- &e%permission% &7- ";

    @Value
    private String contentMinecraftCommandLayout = "\n&eAnd give access to commands\n%list%";

    @Value
    @Global("contentMinecraftCommandListElementRepresentation")
    private String contentMinecraftCommandElement = "&7- &eAccess to %command%";

    @Value
    private String contentOptionalToggleTrue = "&a&nEnable";

    @Value
    private String contentOptionalToggleFalse = "&c&nDisable";

    @Value
    private String acceptButton = "&aAccept";

    @Value
    private String declineButton = "&cDecline";

    @Value
    private List<String> linkMessageComponents = Arrays.asList(
            "title", "newline",
            "content", "newline",
            "accept", " ", "decline"
    );

    @Value
    private String enabledEnd = "&aSuccess!";

    @Value
    private String disabledEnd = "&cDeclined!";

    public LinkingModuleConfig(ConfigData configData) {
        load(configData);
    }
}
