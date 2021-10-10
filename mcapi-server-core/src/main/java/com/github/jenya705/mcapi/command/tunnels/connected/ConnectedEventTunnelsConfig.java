package com.github.jenya705.mcapi.command.tunnels.connected;

import com.github.jenya705.mcapi.command.advanced.AdvancedCommandExecutorConfig;
import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.data.loadable.Global;
import com.github.jenya705.mcapi.data.loadable.Value;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jenya705
 */
@Getter
@Setter
@ToString
public class ConnectedEventTunnelsConfig extends AdvancedCommandExecutorConfig {

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
