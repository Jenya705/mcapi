package com.github.jenya705.mcapi.server.inventory;

import com.github.jenya705.mcapi.UUIDHolder;
import com.github.jenya705.mcapi.block.BlockData;
import com.github.jenya705.mcapi.event.InventoryMoveEvent;
import com.github.jenya705.mcapi.inventory.InventoryHolder;
import com.github.jenya705.mcapi.permission.Permissions;
import com.github.jenya705.mcapi.rest.event.RestInventoryMoveEvent;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.github.jenya705.mcapi.server.event.EventLoop;
import com.github.jenya705.mcapi.server.module.permission.PermissionManager;
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
    private final PermissionManager permissionManager;

    @Inject
    public InventoryMoveEventHandler(EventLoop eventLoop, EventTunnel eventTunnel, PermissionManager permissionManager) {
        this.eventTunnel = eventTunnel;
        this.permissionManager = permissionManager;
        eventLoop.asyncHandler(InventoryMoveEvent.class, this::handleEvent);
    }

    private void handleEvent(InventoryMoveEvent event) {
        eventTunnel.broadcast(
                event, RestInventoryMoveEvent.type,
                client -> permissionManager.hasPermissionInventoryMove(client.getOwner(), event)
        );
    }

    public static boolean testClient(AbstractBot bot, InventoryMoveEvent event) {
        return permissionPredicate(event.getInitiator()).test(bot) ||
                permissionPredicate(event.getSource()).test(bot) ||
                permissionPredicate(event.getDestination()).test(bot);
    }

    public static Predicate<AbstractBot> permissionPredicate(InventoryHolder holder) {
        if (holder instanceof BlockData) {
            return bot -> bot.hasPermission(
                    Permissions.EVENT_TUNNEL_INVENTORY_MOVE_BLOCK,
                    ((BlockData) holder).getBlock()
            );
        }
        else if (holder instanceof UUIDHolder) {
            return bot -> bot.hasPermission(
                    Permissions.EVENT_TUNNEL_INVENTORY_MOVE_UUID_HOLDER,
                    (UUIDHolder) holder
            );
        }
        return it -> false;
    }

}
