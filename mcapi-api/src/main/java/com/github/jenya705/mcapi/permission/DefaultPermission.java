package com.github.jenya705.mcapi.permission;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jenya705
 */
@Getter
@AllArgsConstructor
public enum DefaultPermission {

    PLAYER_GET(Permissions.PLAYER_GET, false, false, true),
    PLAYER_GET_LOCATION(Permissions.PLAYER_GET_LOCATION, false, false, true),
    PLAYER_LIST(Permissions.PLAYER_LIST, true, false, true),
    PLAYER_HAS_PERMISSION(Permissions.PLAYER_HAS_PERMISSION, false, false, true),
    EVENT_TUNNEL_MESSAGE(Permissions.EVENT_TUNNEL_MESSAGE, true, false, true),
    EVENT_TUNNEL_COMMAND(Permissions.EVENT_TUNNEL_COMMAND_INTERACTION, true, false, true),
    EVENT_TUNNEL_LINK(Permissions.EVENT_TUNNEL_LINK, true, false, true),
    EVENT_TUNNEL_UNLINK(Permissions.EVENT_TUNNEL_UNLINK, true, false, true),
    EVENT_TUNNEL_JOIN(Permissions.EVENT_TUNNEL_JOIN, true, false, true),
    EVENT_TUNNEL_QUIT(Permissions.EVENT_TUNNEL_QUIT, true, false, true),
    EVENT_TUNNEL_BLOCK_BREAK(Permissions.EVENT_TUNNEL_BLOCK_BREAK, false, false, false),
    EVENT_TUNNEL_BLOCK_PLACE(Permissions.EVENT_TUNNEL_BLOCK_PLACE, false, false, false),
    EVENT_TUNNEL_CAPTURED_ENTITY_CLICK(Permissions.EVENT_TUNNEL_CAPTURED_ENTITY_CLICK, true, false, true),
    LINK_REQUEST(Permissions.LINK_REQUEST, true, false, true),
    COMMAND_CREATE(Permissions.COMMAND_CREATE, true, false, true),
    PLAYER_KICK(Permissions.PLAYER_KICK, false, true, false),
    PLAYER_BAN(Permissions.PLAYER_BAN, false, true, false),
    PLAYER_KILL(Permissions.PLAYER_KILL, false, true, false),
    PLAYER_SEND_MESSAGE(Permissions.PLAYER_SEND_MESSAGE, false, true, true),
    BLOCK_GET(Permissions.BLOCK_GET, true, false, true),
    WORLD_GET(Permissions.WORLD_GET, true, false, true),
    PLAYER_INVENTORY_GET(Permissions.PLAYER_INVENTORY_GET, false, false, true),
    PLAYER_ITEM_GET(Permissions.PLAYER_ITEM_GET, false, false, true),
    BLOCK_INVENTORY_GET(Permissions.BLOCK_INVENTORY_GET, true, false, true),
    BLOCK_ITEM_GET(Permissions.BLOCK_ITEM_GET, true, false, true),
    PLAYER_ENDER_CHEST_GET(Permissions.PLAYER_ENDER_CHEST_GET, false, true, true),
    PLAYER_ENDER_CHEST_ITEM_GET(Permissions.PLAYER_ENDER_CHEST_ITEM_GET, false, true, true),
    PLAYER_OPEN_INVENTORY(Permissions.PLAYER_OPEN_INVENTORY, false, true, false)
    ;

    private final String name;
    private final boolean global;
    private final boolean selector;
    private final boolean enabledDefault;
}
