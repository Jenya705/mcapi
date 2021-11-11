package com.github.jenya705.mcapi.permission;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jenya705
 */
@Getter
@AllArgsConstructor
public enum DefaultPermission {

    USER_GET(Permissions.USER_GET, false, false, true),
    USER_GET_LOCATION(Permissions.USER_GET_LOCATION, false, false, true),
    USER_LIST(Permissions.USER_LIST, true, false, true),
    USER_HAS_PERMISSION(Permissions.USER_HAS_PERMISSION, false, false, true),
    EVENT_TUNNEL_MESSAGE(Permissions.EVENT_TUNNEL_MESSAGE, true, false, true),
    EVENT_TUNNEL_COMMAND(Permissions.EVENT_TUNNEL_COMMAND_INTERACTION, true, false, true),
    EVENT_TUNNEL_LINK(Permissions.EVENT_TUNNEL_LINK, true, false, true),
    EVENT_TUNNEL_UNLINK(Permissions.EVENT_TUNNEL_UNLINK, true, false, true),
    EVENT_TUNNEL_JOIN(Permissions.EVENT_TUNNEL_JOIN, true, false, true),
    EVENT_TUNNEL_QUIT(Permissions.EVENT_TUNNEL_QUIT, true, false, true),
    LINK_REQUEST(Permissions.LINK_REQUEST, true, false, true),
    USER_COMMAND_CREATE(Permissions.USER_COMMAND_CREATE, true, false, true),
    USER_KICK(Permissions.USER_KICK, false, true, false),
    USER_BAN(Permissions.USER_BAN, false, true, false),
    USER_SEND_MESSAGE(Permissions.USER_SEND_MESSAGE, false, true, true),
    BLOCK_GET(Permissions.BLOCK_GET, true, false, true),
    WORLD_GET(Permissions.WORLD_GET, true, false, true),
    COMMAND_BLOCK_GET(Permissions.COMMAND_BLOCK_GET, true, false, true),
    CHEST_BLOCK_GET(Permissions.CHEST_BLOCK_GET, true, false, true),
    BARREL_BLOCK_GET(Permissions.BARREL_BLOCK_GET, true, false, true),
    PLAYER_INVENTORY_GET(Permissions.PLAYER_INVENTORY_GET, false, false, true),
    PLAYER_ITEM_GET(Permissions.PLAYER_ITEM_GET, false, false, true),
    BLOCK_INVENTORY_GET(Permissions.BLOCK_INVENTORY_GET, false, false, true),
    BLOCK_ITEM_GET(Permissions.BLOCK_ITEM_GET, true, false, true)
    ;

    private final String name;
    private final boolean global;
    private final boolean selector;
    private final boolean enabledDefault;
}
