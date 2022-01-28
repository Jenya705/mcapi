package com.github.jenya705.mcapi.server.command.tunnels.subscriptions;

import com.github.jenya705.mcapi.server.data.ConfigData;
import com.github.jenya705.mcapi.server.data.loadable.Global;
import com.github.jenya705.mcapi.server.data.loadable.Value;
import com.github.jenya705.mcapi.server.module.config.Config;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jenya705
 */
@Getter
@Setter
@ToString
public class SubscriptionsEventTunnelsConfig extends Config {

    @Value
    private String listLayout = "&eSubscriptions %name%\n%list%\nPage %page%";

    @Value
    @Global("subscriptionsElementListRepresentation")
    private String listElement = "&7- &e%name%";

    @Value
    @Global("listsDelimiter")
    private String listDelimiter = "&r\n";

    @Value
    @Global("notPermitted")
    private String notPermitted = "&cYou are not permitted to do that";

    @Value
    private String botIsNotConnected = "&cBot is not connected to gateway";

    @Value
    @Global("maxElementsInList")
    private int maxElements = 10;

    public SubscriptionsEventTunnelsConfig(ConfigData configData) {
        load(configData);
    }
}
