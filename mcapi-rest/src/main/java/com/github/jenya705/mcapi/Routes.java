package com.github.jenya705.mcapi;

import lombok.experimental.UtilityClass;

/**
 * @author Jenya705
 */
@UtilityClass
public class Routes {

    public final Route PLAYER = Route.get("/player/{id}");

    public final Route PLAYER_LOCATION = Route.get("/player/{id}/location");

    public final Route SEND_MESSAGE = Route.post("/player/{selector}/send");

    public final Route PLAYER_LIST = Route.get("/players/list");

    public final Route BAN_PLAYER_SELECTOR = Route.put("/player/{selector}/ban");

    public final Route KICK_PLAYER_SELECTOR = Route.delete("/player/{selector}/kick");

    public final Route PLAYER_PERMISSION = Route.get("/player/{id}/permission/{permission}");

    public final Route BOT_LINKED = Route.get("/bot/{selector}/linked");

    public final Route BOT_PERMISSION = Route.get("/bot/{selector}/permission/{permission}");

    public final Route COMMAND_CREATE = Route.post("/command");

    public final Route LINK_REQUEST = Route.post("/player/{id}/link");

    public final Route OFFLINE_PLAYER = Route.get("/offline/{id}");

    public final Route OFFLINE_PLAYER_BAN = Route.put("/offline/{id}/ban");
}
