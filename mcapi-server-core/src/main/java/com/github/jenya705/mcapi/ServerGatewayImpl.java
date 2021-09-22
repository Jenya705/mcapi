package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.entity.api.event.EntityJoinEvent;
import com.github.jenya705.mcapi.entity.api.event.EntityMessageEvent;
import com.github.jenya705.mcapi.entity.api.event.EntityQuitEvent;
import com.github.jenya705.mcapi.entity.event.RestJoinEvent;
import com.github.jenya705.mcapi.entity.event.RestMessageEvent;
import com.github.jenya705.mcapi.entity.event.RestQuitEvent;
import com.github.jenya705.mcapi.event.JoinEvent;
import com.github.jenya705.mcapi.event.MessageEvent;
import com.github.jenya705.mcapi.event.QuitEvent;

/**
 * @author Jenya705
 */
public class ServerGatewayImpl extends AbstractApplicationModule implements ServerGateway {

    @Override
    public void receiveMessage(ApiPlayer player, String message) {
        gateway()
                .broadcast(new EntityMessageEvent(message, player).rest(), RestMessageEvent.type);
    }

    @Override
    public void join(ApiPlayer player) {
        gateway()
                .broadcast(new EntityJoinEvent(player).rest(), RestJoinEvent.type);
    }

    @Override
    public void quit(ApiOfflinePlayer player) {
        gateway()
                .broadcast(new EntityQuitEvent(player).rest(), RestQuitEvent.type);
    }
}
