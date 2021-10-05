package com.github.jenya705.mcapi.module.rest.route.offline;

import com.github.jenya705.mcapi.ApiOfflinePlayer;
import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.module.web.Request;
import com.github.jenya705.mcapi.module.web.Response;
import com.github.jenya705.mcapi.permission.Permissions;

/**
 * @author Jenya705
 */
public class GetOfflinePlayerRouteHandler extends AbstractRouteHandler {

    public GetOfflinePlayerRouteHandler() {
        super(Routes.OFFLINE_PLAYER);
    }

    @Override
    public void handle(Request request, Response response) {
        AbstractBot bot = request.bot();
        ApiOfflinePlayer offlinePlayer = request
                .paramOrException("id", ApiOfflinePlayer.class);
        bot.needPermission(Permissions.USER_GET, offlinePlayer);
        response.ok(offlinePlayer);
    }
}
