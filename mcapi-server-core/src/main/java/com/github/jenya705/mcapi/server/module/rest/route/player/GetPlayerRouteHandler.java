package com.github.jenya705.mcapi.server.module.rest.route.player;

import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.error.PlayerNotFoundException;
import com.github.jenya705.mcapi.permission.Permissions;
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
public class GetPlayerRouteHandler extends AbstractRouteHandler {

    @Inject
    public GetPlayerRouteHandler(ServerApplication application) {
        super(application, Routes.PLAYER);
    }

    @Override
    public Mono<Response> handle(Request request) {
        String playerId = request.paramOrException("id");
        return core()
                .getPlayerById(playerId)
                .flatMap(player -> ReactorUtils.ifNullError(
                        player, () -> PlayerNotFoundException.create(playerId)))
                .flatMap(request.bot().mapUuidHolderPermission(Permissions.PLAYER_GET))
                .map(player -> Response.create().ok(player));
    }
}
