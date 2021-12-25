package com.github.jenya705.mcapi.module.menu;

import com.github.jenya705.mcapi.entity.event.EntityMenuClickEvent;
import com.github.jenya705.mcapi.menu.MenuCallback;
import com.github.jenya705.mcapi.menu.MenuCallbackType;
import com.github.jenya705.mcapi.module.web.tunnel.EventTunnel;
import com.github.jenya705.mcapi.permission.Permissions;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.rest.event.RestMenuClickEvent;
import lombok.AllArgsConstructor;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class EventTunnelMenuCallback implements MenuCallback {

    private final String id;
    private final int botId;
    private final EventTunnel eventTunnel;

    @Override
    public void clicked(Player player) {
        eventTunnel
                .getClients()
                .stream()
                .filter(it -> it.getOwner().getEntity().getId() == botId)
                .filter(it -> it.isSubscribed(RestMenuClickEvent.type))
                .forEach(it -> it.send(RestMenuClickEvent.from(
                        new EntityMenuClickEvent(id, player)
                )));
    }

    @Override
    public MenuCallbackType getType() {
        return MenuCallbackType.EVENT_TUNNEL;
    }

    @Override
    public String getData() {
        return id;
    }
}
