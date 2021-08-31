package com.github.jenya705.mcapi.rest.player;

import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.JerseyClass;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.error.PlayerNotFoundException;
import com.github.jenya705.mcapi.module.authorization.AuthorizationModule;
import com.github.jenya705.mcapi.util.Selector;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

/**
 * @author Jenya705
 */
@JerseyClass
@Path("/player/{name}")
public class PlayerPunishmentRest implements BaseCommon {

    private final AuthorizationModule authorization = bean(AuthorizationModule.class);

    @POST
    @Path("/kick")
    public Response kick(
            @PathParam("name") String name,
            @HeaderParam("Authorization") String authorizationHeader,
            String reason
    ) {
        AbstractBot bot = authorization.bot(authorizationHeader);
        Selector<ApiPlayer> selector = core()
                .getPlayersBySelector(name, bot);
        if (selector.isEmpty()) {
            throw new PlayerNotFoundException(name);
        }
        bot.needPermission("user.kick" + selector.getPermissionName(), selector.getTarget());
        selector.forEach(player -> player.kick(reason));
        return Response
                .noContent()
                .build();
    }

    @PATCH
    @Path("/ban")
    public Response ban(
            @PathParam("name") String name,
            @HeaderParam("Authorization") String authorizationHeader,
            String reason
    ) {
        AbstractBot bot = authorization.bot(authorizationHeader);
        Selector<ApiPlayer> selector = core()
                .getPlayersBySelector(name, bot);
        if (selector.isEmpty()) {
            throw new PlayerNotFoundException(name);
        }
        bot.needPermission("user.ban" + selector.getPermissionName(), selector.getTarget());
        selector.forEach(player -> player.ban(reason));
        return Response
                .noContent()
                .build();
    }
}
