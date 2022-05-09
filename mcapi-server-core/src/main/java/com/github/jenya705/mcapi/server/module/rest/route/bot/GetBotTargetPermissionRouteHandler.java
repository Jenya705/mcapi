package com.github.jenya705.mcapi.server.module.rest.route.bot;

import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.entity.EntityPermission;
import com.github.jenya705.mcapi.error.SelectorEmptyException;
import com.github.jenya705.mcapi.player.OfflinePlayer;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.github.jenya705.mcapi.server.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.server.module.selector.SelectorProvider;
import com.github.jenya705.mcapi.server.module.web.Request;
import com.github.jenya705.mcapi.server.module.web.Response;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import reactor.core.publisher.Mono;

/**
 * @author Jenya705
 */
@Singleton
public class GetBotTargetPermissionRouteHandler extends AbstractRouteHandler {

    private final SelectorProvider selectorProvider;

    @Inject
    public GetBotTargetPermissionRouteHandler(ServerApplication application, SelectorProvider selectorProvider) {
        super(application, Routes.BOT_TARGET_PERMISSION);
        this.selectorProvider = selectorProvider;
    }

    @Override
    public Mono<Response> handle(Request request) {
        AbstractBot bot = request.bot();
        String playerId = request.paramOrException("target");
        String permission = request.paramOrException("permission");
        return selectorProvider
                .bots(request.paramOrException("selector"), bot)
                .map(selector -> selector
                        .all()
                        .stream()
                        .findAny()
                        .orElseThrow(SelectorEmptyException::create)
                )
                .flatMap(selectorBot -> core()
                        .getOfflinePlayerById(playerId)
                        .map(player -> new EntityPermission(
                                selectorBot.hasPermission(permission, player),
                                permission,
                                player.getUuid()
                        ))
                )
                .map(entityPermission -> Response.create().ok(entityPermission));
    }
}
