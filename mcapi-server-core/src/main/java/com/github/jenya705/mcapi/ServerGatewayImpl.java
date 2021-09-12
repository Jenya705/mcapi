package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.event.GatewayJoinEvent;
import com.github.jenya705.mcapi.event.GatewayMessageEvent;
import com.github.jenya705.mcapi.event.GatewayQuitEvent;

/**
 * @author Jenya705
 */
public class ServerGatewayImpl implements ServerGateway, BaseCommon {

    @Override
    public void receiveMessage(ApiPlayer player, String message) {
        gateway()
                .broadcast(GatewayMessageEvent.of(message, player));
    }

    @Override
    public void join(ApiPlayer player) {
        gateway()
                .broadcast(GatewayJoinEvent.of(player));
    }

    @Override
    public void quit(ApiOfflinePlayer player) {
        gateway()
                .broadcast(GatewayQuitEvent.of(player));
    }
}
