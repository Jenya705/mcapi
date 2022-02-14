package com.github.jenya705.mcapi.server.inventory;

import com.github.jenya705.mcapi.UUIDHolder;
import com.github.jenya705.mcapi.block.BlockData;
import com.github.jenya705.mcapi.event.InventoryMoveEvent;
import com.github.jenya705.mcapi.inventory.InventoryHolder;
import com.github.jenya705.mcapi.permission.Permissions;
import com.github.jenya705.mcapi.rest.event.RestInventoryMoveEvent;
import com.github.jenya705.mcapi.server.event.EventLoop;
import com.github.jenya705.mcapi.server.module.web.tunnel.EventTunnel;
import com.github.jenya705.mcapi.server.module.web.tunnel.EventTunnelClient;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.function.Predicate;

/**
 * @author Jenya705
 */
@Singleton
public class InventoryMoveEventHandler {

    private final EventTunnel eventTunnel;

    @Inject
    public InventoryMoveEventHandler(EventLoop eventLoop, EventTunnel eventTunnel) {
        this.eventTunnel = eventTunnel;
        eventLoop.asyncHandler(InventoryMoveEvent.class, this::handleEvent);
    }

    private void handleEvent(InventoryMoveEvent event) {
        Predicate<EventTunnelClient> initiatorPredicate = permissionPredicate(event.getInitiator());
        Predicate<EventTunnelClient> sourcePredicate = permissionPredicate(event.getSource());
        Predicate<EventTunnelClient> destinationPredicate = permissionPredicate(event.getDestination());
        eventTunnel
                .getClients()
                .stream()
                .filter(it -> it.isSubscribed(RestInventoryMoveEvent.type))
                .filter(client ->
                        initiatorPredicate.test(client) ||
                        sourcePredicate.test(client) ||
                        destinationPredicate.test(client)
                )
                .forEach(it -> it.send(event));
    }

    private Predicate<EventTunnelClient> permissionPredicate(InventoryHolder holder) {
        if (holder instanceof BlockData) {
            return client -> client.getOwner().hasPermission(
                    Permissions.EVENT_TUNNEL_INVENTORY_MOVE_BLOCK,
                    ((BlockData) holder).getBlock()
            );
        }
        else if (holder instanceof UUIDHolder) {
            return client -> client.getOwner().hasPermission(
                    Permissions.EVENT_TUNNEL_INVENTORY_MOVE_UUID_HOLDER,
                    (UUIDHolder) holder
            );
        }
        return it -> false;
    }

}
