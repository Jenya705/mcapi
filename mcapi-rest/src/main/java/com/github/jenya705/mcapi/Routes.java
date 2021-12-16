package com.github.jenya705.mcapi;

import lombok.experimental.UtilityClass;

/**
 * @author Jenya705
 */
@UtilityClass
public class Routes {

    public final Route STATUS = Route.get("/status");

    public final Route PLAYER = Route.get("/player/{id}");

    public final Route PLAYER_LOCATION = Route.get("/player/{id}/location");

    public final Route SEND_MESSAGE = Route.post("/player/{selector}/send");

    public final Route BAN_PLAYER_SELECTOR = Route.put("/player/{selector}/ban");

    public final Route KICK_PLAYER_SELECTOR = Route.delete("/player/{selector}/kick");

    public final Route KILL_PLAYER_SELECTOR = Route.delete("/player/{selector}/kill");

    public final Route PLAYER_PERMISSION = Route.get("/player/{id}/permission/{permission}");

    public final Route PLAYER_INVENTORY = Route.get("/player/{id}/inventory");

    public final Route PLAYER_INVENTORY_ITEM = Route.get("/player/{id}/inventory/{item}");

    public final Route LINK_REQUEST = Route.post("/player/{id}/link");

    public final Route PLAYER_LIST = Route.get("/players/list");

    public final Route OFFLINE_PLAYER = Route.get("/offline/{id}");

    public final Route OFFLINE_PLAYER_BAN = Route.put("/offline/{selector}/ban");

    public final Route BOT_LINKED = Route.get("/bot/{selector}/linked");

    public final Route BOT_PERMISSION = Route.get("/bot/{selector}/permission/{permission}");

    public final Route BOT_TARGET_PERMISSION = Route.get("/bot/{selector}/permission/{permission}/{target}");

    public final Route COMMAND_CREATE = Route.post("/command");

    public final Route COMMAND_DELETE = Route.delete("/command/{name}");

    public final Route WORLD = Route.get("/world/{id}");

    public final Route BLOCK = Route.get("/world/{id}/block/{x}/{y}/{z}");

    public final Route BLOCK_DATA = Route.get("/world/{id}/block/{x}/{y}/{z}/data");

    public final Route BLOCK_INVENTORY = Route.get("/world/{id}/block/{x}/{y}/{z}/data/inventory");

    public final Route BLOCK_INVENTORY_ITEM = Route.get("/world/{id}/block/{x}/{y}/{z}/data/inventory/{item}");

    public final Route BLOCK_DATA_FIELD = Route.put("/world/{id}/block/{x}/{y}/{z}/data/{name}");

    public final Route PLAYER_ENDER_CHEST = Route.get("/player/{id}/ender");

    public final Route PLAYER_ENDER_CHEST_ITEM = Route.get("/player/{id}/ender/{item}");

    public final Route ENTITY = Route.get("/entity/{id}");

    public final Route REGISTER_ENTITY = Route.post("/entity");

    public final Route UPDATE_ENTITY = Route.patch("/entity/{id}");

}
