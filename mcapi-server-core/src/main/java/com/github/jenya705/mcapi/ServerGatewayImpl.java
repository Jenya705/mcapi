package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.gateway.object.MessageReceivedObject;

/**
 * @author Jenya705
 */
public class ServerGatewayImpl implements ServerGateway, BaseCommon {

    @Override
    public void receiveMessage(ApiPlayer player, String message) {
        app()
                .getGateway()
                .broadcast(MessageReceivedObject.of(message, player));
    }
}
