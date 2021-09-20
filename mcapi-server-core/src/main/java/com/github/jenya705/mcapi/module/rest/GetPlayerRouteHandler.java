package com.github.jenya705.mcapi.module.rest;

import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.OnStartup;
import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.error.PlayerNotFoundException;
import com.github.jenya705.mcapi.module.web.Request;
import com.github.jenya705.mcapi.module.web.Response;
import com.github.jenya705.mcapi.module.web.RouteHandler;
import com.github.jenya705.mcapi.module.web.WebServer;

/**
 * @author Jenya705
 */
public class GetPlayerRouteHandler implements RouteHandler, BaseCommon {

    @OnStartup
    public void start() {
        bean(WebServer.class).addHandler(Routes.PLAYER, this);
    }

    @Override
    public void handle(Request request, Response response) {
        String id = request.param("id").block();
        ApiPlayer player =
                core()
                        .getOptionalPlayerId(id)
                        .orElseThrow(() -> new PlayerNotFoundException(id));
        request
                .header("Authorization", AbstractBot.class)
                .block()
                .needPermission("user.get", player);
        response
                .status(200)
                .body(player);
    }
}
