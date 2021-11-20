package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.entity.event.EntityJoinEvent;
import com.github.jenya705.mcapi.entity.event.EntityMessageEvent;
import com.github.jenya705.mcapi.entity.event.EntityQuitEvent;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.rest.event.RestJoinEvent;
import com.github.jenya705.mcapi.rest.event.RestMessageEvent;
import com.github.jenya705.mcapi.rest.event.RestQuitEvent;

/**
 * @author Jenya705
 */
public class ServerLocalEventHandlerImpl extends AbstractApplicationModule implements ServerLocalEventHandler {

    @Override
    public void receiveMessage(Player player, String message) {
        eventTunnel()
                .broadcast(new EntityMessageEvent(message, player).rest(), RestMessageEvent.type);
    }

    @Override
    public void join(Player player) {
        eventTunnel()
                .broadcast(new EntityJoinEvent(player).rest(), RestJoinEvent.type);
    }

    @Override
    public void quit(OfflinePlayer player) {
        eventTunnel()
                .broadcast(new EntityQuitEvent(player).rest(), RestQuitEvent.type);
    }
}
