package com.github.jenya705.mcapi.module.rest.route.player;

import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.UUIDHolder;
import com.github.jenya705.mcapi.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.module.web.Request;
import com.github.jenya705.mcapi.module.web.Response;
import com.github.jenya705.mcapi.permission.Permissions;
import com.github.jenya705.mcapi.rest.RestPlayerList;

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
                .needPermission(Permissions.USER_LIST);
        response.ok(new RestPlayerList(
                core()
                        .getPlayers()
                        .stream()
                        .map(UUIDHolder::getUuid)
                        .toArray(UUID[]::new)
        ));
    }
}
