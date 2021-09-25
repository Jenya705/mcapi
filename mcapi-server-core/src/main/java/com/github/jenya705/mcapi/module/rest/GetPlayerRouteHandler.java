package com.github.jenya705.mcapi.module.rest;

import com.github.jenya705.mcapi.*;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.error.AuthorizationBadTokenException;
import com.github.jenya705.mcapi.module.web.Request;
import com.github.jenya705.mcapi.module.web.Response;
import com.github.jenya705.mcapi.module.web.RouteHandler;
import com.github.jenya705.mcapi.module.web.WebServer;
import com.github.jenya705.mcapi.util.ReactiveUtils;

/**
 * @author Jenya705
 */
public class GetPlayerRouteHandler extends AbstractApplicationModule implements RouteHandler {

    @OnStartup
    public void start() {
        bean(WebServer.class).addHandler(Routes.PLAYER, this);
    }

    @Override
    public void handle(Request request, Response response) {
        ApiPlayer player = request
                .param("id", ApiPlayer.class)
                .orElseThrow(ReactiveUtils::unknownException);
        request
                .header("Authorization", AbstractBot.class)
                .orElseThrow(AuthorizationBadTokenException::new)
                .needPermission("user.get", player);
        response.ok(player);
    }
}
