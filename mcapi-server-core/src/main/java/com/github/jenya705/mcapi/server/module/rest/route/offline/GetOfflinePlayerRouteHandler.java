package com.github.jenya705.mcapi.server.module.rest.route.offline;

import com.github.jenya705.mcapi.player.OfflinePlayer;
import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.permission.Permissions;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.github.jenya705.mcapi.server.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.server.module.web.Request;
import com.github.jenya705.mcapi.server.module.web.Response;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Jenya705
 */
@Singleton
public class GetOfflinePlayerRouteHandler extends AbstractRouteHandler {

    @Inject
    public GetOfflinePlayerRouteHandler(ServerApplication application) {
        super(application, Routes.OFFLINE_PLAYER);
    }

    @Override
    public void handle(Request request, Response response) {
        AbstractBot bot = request.bot();
        OfflinePlayer offlinePlayer = request
                .paramOrException("id", OfflinePlayer.class);
        bot.needPermission(Permissions.PLAYER_GET, offlinePlayer);
        response.ok(offlinePlayer);
    }
}
