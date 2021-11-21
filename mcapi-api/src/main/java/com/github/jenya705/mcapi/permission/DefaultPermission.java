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
    LINK_REQUEST(Permissions.LINK_REQUEST, true, false, true),
    PLAYER_COMMAND_CREATE(Permissions.PLAYER_COMMAND_CREATE, true, false, true),
    PLAYER_KICK(Permissions.PLAYER_KICK, false, true, false),
    PLAYER_BAN(Permissions.PLAYER_BAN, false, true, false),
    PLAYER_SEND_MESSAGE(Permissions.PLAYER_SEND_MESSAGE, false, true, true),
    BLOCK_GET(Permissions.BLOCK_GET, true, false, true),
    WORLD_GET(Permissions.WORLD_GET, true, false, true),
    COMMAND_BLOCK_GET(Permissions.COMMAND_BLOCK_GET, true, false, true),
    CHEST_BLOCK_GET(Permissions.CHEST_BLOCK_GET, true, false, true),
    BARREL_BLOCK_GET(Permissions.BARREL_BLOCK_GET, true, false, true),
    FURNACE_BLOCK_GET(Permissions.FURNACE_BLOCK_GET, true, false, true),
    SMOKER_BLOCK_GET(Permissions.SMOKER_BLOCK_GET, true, false, true),
    BLAST_FURNACE_BLOCK_GET(Permissions.BLAST_FURNACE_BLOCK_GET, true, false, true),
    BREWING_STAND_BLOCK_GET(Permissions.BREWING_STAND_BLOCK_GET, true, false, true),
    CAMPFIRE_BLOCK_GET(Permissions.CAMPFIRE_BLOCK_GET, true, false, true),
    ENDER_CHEST_BLOCK_GET(Permissions.ENDER_CHEST_BLOCK_GET, true, false, true),
    PLAYER_INVENTORY_GET(Permissions.PLAYER_INVENTORY_GET, false, false, true),
    PLAYER_ITEM_GET(Permissions.PLAYER_ITEM_GET, false, false, true),
    BLOCK_INVENTORY_GET(Permissions.BLOCK_INVENTORY_GET, true, false, true),
    BLOCK_ITEM_GET(Permissions.BLOCK_ITEM_GET, true, false, true),
    PLAYER_ENDER_CHEST_GET(Permissions.PLAYER_ENDER_CHEST_GET, false, true, true),
    PLAYER_ENDER_CHEST_ITEM_GET(Permissions.PLAYER_ENDER_CHEST_ITEM_GET, false, true, true)
    ;

    private final String name;
    private final boolean global;
    private final boolean selector;
    private final boolean enabledDefault;
}
