package com.github.jenya705.mcapi.module.rest.route;

import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.error.AuthorizationBadTokenException;
import com.github.jenya705.mcapi.module.web.Request;
import com.github.jenya705.mcapi.module.web.Response;
import com.github.jenya705.mcapi.util.ReactiveUtils;

/**
 * @author Jenya705
 */
public class GetPlayerRouteHandler extends AbstractRouteHandler {

    public GetPlayerRouteHandler() {
        super(Routes.PLAYER);
    }

    @Override
    public void handle(Request request, Response response) {
        ApiPlayer player = request
                .paramOrException("id", ApiPlayer.class);
        request
                .bot()
                .needPermission("user.get", player);
        response.ok(player);
    }
}
