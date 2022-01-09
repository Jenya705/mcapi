package com.github.jenya705.mcapi.server.module.rest.route.player;

import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.UUIDHolder;
import com.github.jenya705.mcapi.permission.Permissions;
import com.github.jenya705.mcapi.rest.player.RestPlayerList;
import com.github.jenya705.mcapi.server.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.server.module.web.Request;
import com.github.jenya705.mcapi.server.module.web.Response;

import java.util.UUID;

/**
 * @author Jenya705
 */
public class GetPlayerListRouteHandler extends AbstractRouteHandler {

    public GetPlayerListRouteHandler() {
        super(Routes.PLAYER_LIST);
    }

    @Override
    public void handle(Request request, Response response) {
        request
                .bot()
                .needPermission(Permissions.PLAYER_LIST);
        response.ok(new RestPlayerList(
                core()
                        .getPlayers()
                        .stream()
                        .map(UUIDHolder::getUuid)
                        .toArray(UUID[]::new)
        ));
    }
}
