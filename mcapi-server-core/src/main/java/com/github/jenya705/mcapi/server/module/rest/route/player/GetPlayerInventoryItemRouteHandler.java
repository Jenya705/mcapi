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

import java.util.Objects;
import java.util.Optional;

/**
 * @author Jenya705
 */
@Singleton
public class GetPlayerInventoryItemRouteHandler extends AbstractRouteHandler {

    @Inject
    public GetPlayerInventoryItemRouteHandler(ServerApplication application) {
        super(application, Routes.PLAYER_INVENTORY_ITEM);
    }

    @Override
    public Mono<Response> handle(Request request) {
        String playerId = request.paramOrException("id");
        int itemId = request.paramOrException("item", int.class);
        return core()
                .getPlayerById(playerId)
                .flatMap(player -> ReactorUtils.ifNullError(
                        player, () -> PlayerNotFoundException.create(playerId)))
                .flatMap(request.bot().mapUuidHolderPermission(Permissions.PLAYER_ITEM_GET))
                .map(player -> Objects.requireNonNullElse(
                        player.getInventory().getItem(itemId), ItemUtils.empty()
                ))
                .map(item -> Response.create().ok(item));
    }
}
