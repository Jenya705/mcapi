package com.github.jenya705.mcapi.command.tunnels.connected;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.ServerApplication;
import com.github.jenya705.mcapi.command.advanced.AdvancedCommandExecutor;
import com.github.jenya705.mcapi.data.ConfigData;

import java.util.Collections;

/**
 * @author Jenya705
 */
public class ConnectedEventTunnelsCommand extends AdvancedCommandExecutor<ConnectedEventTunnelsArguments> {

    private ConnectedEventTunnelsConfig config;

    public ConnectedEventTunnelsCommand(ServerApplication application) {
        super(application, ConnectedEventTunnelsArguments.class);
        this
                .tab(() -> Collections.singletonList("<page>"));
    }

    @Override
    public void onCommand(CommandSender sender, ConnectedEventTunnelsArguments args, String permission) {
        sendListMessage(
                sender,
                config.getListLayout(),
                config.getListElement(),
                config.getListDelimiter(),
                eventTunnel().getClients(),
                client -> new String[]{
                        "%name%", client.getOwner().getEntity().getName()
                },
                config.getMaxElements(),
                args.getPage(),
                "%page%", Integer.toString(args.getPage() + 1)
        );
    }

    @Override
    public void setConfig(ConfigData config) {
        this.config = new ConnectedEventTunnelsConfig(config);
        setConfig(this.config);
    }
}
