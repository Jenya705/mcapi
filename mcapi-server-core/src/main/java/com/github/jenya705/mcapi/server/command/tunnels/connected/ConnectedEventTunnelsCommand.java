package com.github.jenya705.mcapi.server.command.tunnels.connected;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.command.advanced.AdvancedCommandExecutor;
import com.github.jenya705.mcapi.server.data.ConfigData;
import com.google.inject.Inject;

import java.util.Collections;

/**
 * @author Jenya705
 */
public class ConnectedEventTunnelsCommand extends AdvancedCommandExecutor<ConnectedEventTunnelsArguments> {

    private ConnectedEventTunnelsConfig config;

    @Inject
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
                        "%name%",
                        client.getOwner() == null || client.getOwner().getEntity() == null
                                ? "unknown" : client.getOwner().getEntity().getName()
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
