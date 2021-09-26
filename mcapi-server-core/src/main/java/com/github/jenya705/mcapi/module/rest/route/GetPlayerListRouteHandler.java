package com.github.jenya705.mcapi.module.rest.route;

import com.github.jenya705.mcapi.ApiUUID;
import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.entity.RestPlayerList;
import com.github.jenya705.mcapi.module.web.Request;
import com.github.jenya705.mcapi.module.web.Response;

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
                .needPermission("user.list");
        response.ok(new RestPlayerList(
                core()
                        .getPlayers()
                        .stream()
                        .map(ApiUUID::getUuid)
                        .toArray(UUID[]::new)
        ));
    }
}
