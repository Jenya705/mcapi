package com.github.jenya705.mcapi.server.module.rest.route.player;

import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.error.PlayerNotFoundException;
import com.github.jenya705.mcapi.permission.Permissions;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.server.module.web.Request;
import com.github.jenya705.mcapi.server.module.web.Response;
import com.github.jenya705.mcapi.server.util.ReactorUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import reactor.core.publisher.Mono;

/**
 * @author Jenya705
 */
@Singleton
public class GetPlayerLocationRouteHandler extends AbstractRouteHandler {

    @Inject
    public GetPlayerLocationRouteHandler(ServerApplication application) {
        super(application, Routes.PLAYER_LOCATION);
    }

    @Override
    public Mono<Response> handle(Request request) {
        String playerId = request.paramOrException("id");
        request
                .bot()
                .needPermission(Permissions.PLAYER_GET_LOCATION);
        return core()
                .getPlayerById(playerId)
                .switchIfEmpty(Mono.error(() -> PlayerNotFoundException.create(playerId)))
                .map(Player::getLocation)
                .map(location -> Response.create().ok(location));
    }
}
