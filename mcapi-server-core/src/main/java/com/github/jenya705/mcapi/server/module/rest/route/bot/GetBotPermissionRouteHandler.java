package com.github.jenya705.mcapi.server.module.rest.route.bot;

import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.entity.EntityPermission;
import com.github.jenya705.mcapi.error.SelectorEmptyException;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.github.jenya705.mcapi.server.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.server.module.selector.SelectorProvider;
import com.github.jenya705.mcapi.server.module.web.Request;
import com.github.jenya705.mcapi.server.module.web.Response;
import com.github.jenya705.mcapi.server.util.Selector;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import reactor.core.publisher.Mono;

/**
 * @author Jenya705
 */
@Singleton
public class GetBotPermissionRouteHandler extends AbstractRouteHandler {

    private final SelectorProvider selectorProvider;

    @Inject
    public GetBotPermissionRouteHandler(ServerApplication application, SelectorProvider selectorProvider) {
        super(application, Routes.BOT_PERMISSION);
        this.selectorProvider = selectorProvider;
    }

    @Override
    public Mono<Response> handle(Request request) {
        AbstractBot bot = request.bot();
        String permission = request.paramOrException("permission");
        return selectorProvider
                .bots(request.paramOrException("selector"), bot)
                .flatMap(Selector::errorIfEmpty)
                .map(selector -> selector
                        .all()
                        .stream()
                        .findAny()
                        .orElseThrow(SelectorEmptyException::create)
                )
                .map(selectorBot -> new EntityPermission(
                        selectorBot.hasPermission(permission),
                        permission,
                        null
                ))
                .map(entityPermission -> Response.create().ok(entityPermission));
    }
}
