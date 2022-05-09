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
public class GetPlayerEnderChestRouteHandler extends AbstractRouteHandler {

    @Inject
    public GetPlayerEnderChestRouteHandler(ServerApplication application) {
        super(application, Routes.PLAYER_ENDER_CHEST);
    }

    @Override
    public Mono<Response> handle(Request request) {
        String playerId = request.paramOrException("id");
        return core()
                .getPlayerById(playerId)
                .flatMap(player -> ReactorUtils.ifNullError(
                        player, () -> PlayerNotFoundException.create(playerId)))
                .flatMap(request.bot().mapUuidHolderPermission(Permissions.PLAYER_ENDER_CHEST_GET))
                .map(Player::getEnderChest)
                .map(enderChest -> Response.create().ok(enderChest));
    }
}
