package com.github.jenya705.mcapi.server.module.rest.route.player;

import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.error.PlayerNotFoundException;
import com.github.jenya705.mcapi.permission.Permissions;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.server.module.web.Request;
import com.github.jenya705.mcapi.server.module.web.Response;
import com.github.jenya705.mcapi.server.util.ItemUtils;
import com.github.jenya705.mcapi.server.util.ReactorUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * @author Jenya705
 */
@Singleton
public class GetPlayerEnderChestItemRouteHandler extends AbstractRouteHandler {

    @Inject
    public GetPlayerEnderChestItemRouteHandler(ServerApplication application) {
        super(application, Routes.PLAYER_ENDER_CHEST_ITEM);
    }

    @Override
    public Mono<Response> handle(Request request) {
        String playerId = request.paramOrException("id");
        int itemId = request.paramOrException("item", int.class);
        return core()
                .getPlayerById(playerId)
                .flatMap(player -> ReactorUtils.ifNullError(
                        player, () -> PlayerNotFoundException.create(playerId)))
                .flatMap(request.bot().mapUuidHolderPermission(Permissions.PLAYER_ENDER_CHEST_ITEM_GET))
                .map(player -> player.getEnderChest().getItem(itemId))
                .map(item -> Response.create().ok(item));
    }
}
