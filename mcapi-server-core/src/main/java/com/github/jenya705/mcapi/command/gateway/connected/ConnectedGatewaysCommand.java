package com.github.jenya705.mcapi.command.gateway.connected;

import com.github.jenya705.mcapi.ApiCommandSender;
import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.command.advanced.AdvancedCommandExecutor;
import com.github.jenya705.mcapi.data.ConfigData;

import java.util.Collections;

/**
 * @author Jenya705
 */
public class ConnectedGatewaysCommand extends AdvancedCommandExecutor<ConnectedGatewaysArguments> implements BaseCommon {

    private ConnectedGatewaysConfig config;

    public ConnectedGatewaysCommand() {
        super(ConnectedGatewaysArguments.class);
        this
                .tab(() -> Collections.singletonList("<page>"));
    }

    @Override
    public void onCommand(ApiCommandSender sender, ConnectedGatewaysArguments args, String permission) {
        sendListMessage(
                sender,
                config.getListLayout(),
                config.getListElement(),
                config.getListDelimiter(),
                app().getGateway().getClients(),
                client -> new String[]{
                        "%name%", client.getEntity().getName()
                },
                config.getMaxElements(),
                args.getPage(),
                "%page%", Integer.toString(args.getPage() + 1)
        );
    }

    @Override
    public void setConfig(ConfigData config) {
        this.config = new ConnectedGatewaysConfig(config);
        setConfig(this.config);
    }
}
