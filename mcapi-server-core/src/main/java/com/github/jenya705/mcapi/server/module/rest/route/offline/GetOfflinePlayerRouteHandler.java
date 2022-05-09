package com.github.jenya705.mcapi.server.module.rest.route.offline;

import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.error.PlayerNotFoundException;
import com.github.jenya705.mcapi.permission.Permissions;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
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
public class GetOfflinePlayerRouteHandler extends AbstractRouteHandler {

    @Inject
    public GetOfflinePlayerRouteHandler(ServerApplication application) {
        super(application, Routes.OFFLINE_PLAYER);
    }

    @Override
    public Mono<Response> handle(Request request) {
        AbstractBot bot = request.bot();
        String offlinePlayerId = request.paramOrException("id");
        return core()
                .getOfflinePlayerById(offlinePlayerId)
                .flatMap(player -> ReactorUtils.ifNullError(
                        player, () -> PlayerNotFoundException.create(offlinePlayerId)))
                .flatMap(bot.mapUuidHolderPermission(Permissions.PLAYER_GET))
                .map(player -> Response.create().ok(player));
    }
}
