package com.github.jenya705.mcapi.module.rest.route;

import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.module.web.Request;
import com.github.jenya705.mcapi.module.web.Response;

/**
 * @author Jenya705
 */
public class GetPlayerLocationRouteHandler extends AbstractRouteHandler {

    public GetPlayerLocationRouteHandler() {
        super(Routes.PLAYER_LOCATION);
    }

    @Override
    public void handle(Request request, Response response) {
        ApiPlayer player = request
                .paramOrException("id", ApiPlayer.class);
        request
                .bot()
                .needPermission("user.get.location");
        response.ok(player.getLocation());
    }
}
