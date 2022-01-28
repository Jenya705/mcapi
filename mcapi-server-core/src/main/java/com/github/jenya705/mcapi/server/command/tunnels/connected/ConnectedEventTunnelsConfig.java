package com.github.jenya705.mcapi.server.command.tunnels.connected;

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
public class ConnectedEventTunnelsConfig extends Config {

    @Value
    private String listLayout = "&eConnected event tunnels\n%list%\nPage %page%";

    @Value
    @Global("gatewaysElementListRepresentation")
    private String listElement = "&7- &e%name%";

    @Value
    @Global("listsDelimiter")
    private String listDelimiter = "&r\n";

    @Value
    @Global("maxElementsInList")
    private int maxElements = 10;

    public ConnectedEventTunnelsConfig(ConfigData configData) {
        load(configData);
    }
}
