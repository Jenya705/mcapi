package com.github.jenya705.mcapi.server.module.rest.route.player;

import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.UUIDHolder;
import com.github.jenya705.mcapi.permission.Permissions;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.rest.player.RestPlayerList;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.server.module.web.Request;
import com.github.jenya705.mcapi.server.module.web.Response;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * @author Jenya705
 */
@Singleton
public class GetPlayerListRouteHandler extends AbstractRouteHandler {

    @Inject
    public GetPlayerListRouteHandler(ServerApplication application) {
        super(application, Routes.PLAYER_LIST);
    }

    @Override
    public Mono<Response> handle(Request request) {
        request
                .bot()
                .needPermission(Permissions.PLAYER_LIST);
        return core()
                .getPlayers()
                .map(Player::getUuid)
                .collectList()
                .map(uuids -> new RestPlayerList(uuids.toArray(UUID[]::new)))
                .map(list -> Response.create().ok(list));
    }
}
