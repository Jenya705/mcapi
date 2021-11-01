package com.github.jenya705.mcapi.permission;

import lombok.experimental.UtilityClass;

/**
 * @author Jenya705
 */
@UtilityClass
public class Permissions {

    public final String USER_GET = "user.get";

    public final String USER_GET_LOCATION = "user.get.location";

    public final String USER_LIST = "user.list";

    public final String USER_HAS_PERMISSION = "user.has_permission";

    public final String EVENT_TUNNEL_MESSAGE = "event_tunnel.message";

    public final String EVENT_TUNNEL_COMMAND_INTERACTION = "event_tunnel.command_interaction";

    public final String EVENT_TUNNEL_LINK = "event_tunnel.link";

    public final String EVENT_TUNNEL_UNLINK = "event_tunnel.unlink";

    public final String EVENT_TUNNEL_JOIN = "event_tunnel.join";

    public final String EVENT_TUNNEL_QUIT = "event_tunnel.quit";

    public final String LINK_REQUEST = "link.request";

    public final String USER_COMMAND_CREATE = "user.command.create";

    public final String USER_KICK = "user.kick";

    public final String USER_BAN = "user.ban";

    public final String USER_SEND_MESSAGE = "user.send_message";

    public final String BLOCK_GET = "block.get";

    public final String WORLD_GET = "world.get";

    public final String COMMAND_BLOCK_GET = "block.get.minecraft:command_block";

    public final String CHEST_BLOCK_GET = "block.get.minecraft:chest";

    public final String PLAYER_INVENTORY_GET = "user.get.inventory";

    public final String PLAYER_ITEM_GET = "user.get.inventory.item";

    public final String BLOCK_INVENTORY_GET = "block.get.inventory";

    public final String BLOCK_ITEM_GET = "block.get.inventory.item";

}
