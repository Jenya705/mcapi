package com.github.jenya705.mcapi.permission;

import lombok.experimental.UtilityClass;

/**
 * @author Jenya705
 */
@UtilityClass
public class Permissions {

    public final String PLAYER_GET = "player.get";

    public final String PLAYER_GET_LOCATION = "player.get.location";

    public final String PLAYER_LIST = "player.list";

    public final String PLAYER_HAS_PERMISSION = "player.has_permission";

    public final String EVENT_TUNNEL_MESSAGE = "event_tunnel.message.listen";

    public final String EVENT_TUNNEL_COMMAND_INTERACTION = "event_tunnel.command_interaction.listen";

    public final String EVENT_TUNNEL_LINK = "event_tunnel.player_link.listen";

    public final String EVENT_TUNNEL_UNLINK = "event_tunnel.player_unlink.listen";

    public final String EVENT_TUNNEL_JOIN = "event_tunnel.player_join.listen";

    public final String EVENT_TUNNEL_QUIT = "event_tunnel.player_quit.listen";

    public final String EVENT_TUNNEL_BLOCK_BREAK = "event_tunnel.player_block_break.listen";

    public final String EVENT_TUNNEL_BLOCK_PLACE = "event_tunnel.player_block_place.listen";

    public final String EVENT_TUNNEL_CAPTURED_ENTITY_CLICK = "event_tunnel.captured_entity_click.listen";

    public final String EVENT_TUNNEL_MENU_CLICK = "event_tunnel.menu_click.listen";

    private final String EVENT_TUNNEL_ENTITY_SPAWN = "event_tunnel.entity_spawn";

    public final String EVENT_TUNNEL_ENTITY_SPAWN_LISTEN = EVENT_TUNNEL_ENTITY_SPAWN + ".listen";

    public final String EVENT_TUNNEL_ENTITY_SPAWN_CAPTURED_ENTITY = EVENT_TUNNEL_ENTITY_SPAWN + ".captured_entity";

    public final String EVENT_TUNNEL_ENTITY_SPAWN_ENTITY = EVENT_TUNNEL_ENTITY_SPAWN + ".entity";

    private final String EVENT_TUNNEL_ENTITY_DESPAWN = "event_tunnel.entity_despawn";

    public final String EVENT_TUNNEL_ENTITY_DESPAWN_LISTEN = EVENT_TUNNEL_ENTITY_DESPAWN + ".listen";

    public final String EVENT_TUNNEL_ENTITY_DESPAWN_CAPTURED_ENTITY = EVENT_TUNNEL_ENTITY_DESPAWN + ".captured_entity";

    public final String EVENT_TUNNEL_ENTITY_DESPAWN_ENTITY = EVENT_TUNNEL_ENTITY_DESPAWN + ".entity";

    private final String EVENT_TUNNEL_INVENTORY_MOVE = "event_tunnel.inventory_move.listen";

    public final String EVENT_TUNNEL_INVENTORY_MOVE_LISTEN = EVENT_TUNNEL_INVENTORY_MOVE + ".listen";

    public final String EVENT_TUNNEL_INVENTORY_MOVE_UUID_HOLDER = EVENT_TUNNEL_INVENTORY_MOVE + ".uuid_holder";

    public final String EVENT_TUNNEL_INVENTORY_MOVE_BLOCK = EVENT_TUNNEL_INVENTORY_MOVE + ".block";

    public final String LINK_REQUEST = "link.request";

    public final String COMMAND_CREATE = "command.create";

    public final String PLAYER_KICK = "player.kick";

    public final String PLAYER_BAN = "player.ban";

    public final String PLAYER_KILL = "player.kill";

    public final String PLAYER_SEND_MESSAGE = "player.send_message";

    public final String BLOCK_GET = "block.get";

    public final String WORLD_GET = "world.get";

    public final String PLAYER_INVENTORY_GET = "player.get.inventory";

    public final String PLAYER_ITEM_GET = "player.get.inventory.item";

    public final String BLOCK_INVENTORY_GET = "block.get.inventory";

    public final String BLOCK_ITEM_GET = "block.get.inventory.item";

    public final String BLOCK_DATA = "block.get.data";

    public final String BLOCK_DATA_FIELD = "block.update.data.field";

    public final String PLAYER_ENDER_CHEST_GET = "player.get.ender";

    public final String PLAYER_ENDER_CHEST_ITEM_GET = "player.get.ender.item";

    public final String ENTITY_GET = "entity.get";

    public final String CREATE_ENTITY = "entity.create";

    public final String CAPTURE_ENTITY = "entity.capture";

    public final String UPDATE_ENTITY = "entity.update";

    public final String PLAYER_OPEN_INVENTORY = "player.inventory.open";

    public final String PLAYER_CLOSE_INVENTORY = "player.inventory.close";

}
