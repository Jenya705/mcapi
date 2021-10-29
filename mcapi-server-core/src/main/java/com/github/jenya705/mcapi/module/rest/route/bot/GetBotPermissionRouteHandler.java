package com.github.jenya705.mcapi.module.rest.route.bot;

import com.github.jenya705.mcapi.Bean;
import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.entity.api.EntityPermission;
import com.github.jenya705.mcapi.error.SelectorEmptyException;
import com.github.jenya705.mcapi.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.module.selector.SelectorProvider;
import com.github.jenya705.mcapi.module.web.Request;
import com.github.jenya705.mcapi.module.web.Response;
import com.github.jenya705.mcapi.util.Selector;

/**
 * @author Jenya705
 */
public class GetBotPermissionRouteHandler extends AbstractRouteHandler {

    @Bean
    private SelectorProvider selectorProvider;

    public GetBotPermissionRouteHandler() {
        super(Routes.BOT_PERMISSION);
    }

    @Override
    public void handle(Request request, Response response) {
        AbstractBot bot = request.bot();
        Selector<AbstractBot> botSelector = selectorProvider
                .bots(request.paramOrException("selector"), bot);
        AbstractBot selectorBot = botSelector
                .stream()
                .findAny()
                .orElseThrow(SelectorEmptyException::create);
        String permission = request.paramOrException("permission");
        response.ok(new EntityPermission(
                selectorBot.hasPermission(permission),
                permission,
                null
        ));
    }
}
