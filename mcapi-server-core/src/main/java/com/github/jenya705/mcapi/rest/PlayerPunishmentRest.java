package com.github.jenya705.mcapi.rest;

import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.JerseyClass;
import com.github.jenya705.mcapi.error.PlayerNotFoundException;
import com.github.jenya705.mcapi.module.authorization.AuthorizationModule;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

/**
 * @author Jenya705
 */
@JerseyClass
@Path("/player/{name}")
public class PlayerPunishmentRest implements BaseCommon {

    private final AuthorizationModule authorization = bean(AuthorizationModule.class);

    @DELETE
    @Path("/kick")
    public Response kick(
            @PathParam("name") String name,
            @HeaderParam("Authorization") String authorizationHeader,
            String reason
    ) {
        ApiPlayer player = core()
                .getOptionalPlayerId(name)
                .orElseThrow(() -> new PlayerNotFoundException(name));
        authorization
                .bot(authorizationHeader)
                .needPermission("user.kick", player);
        player.kick(reason);
        return Response
                .ok()
                .build();
    }

    @DELETE
    @Path("/ban")
    public Response ban(
            @PathParam("name") String name,
            @HeaderParam("Authorization") String authorizationHeader,
            String reason
    ) {
        ApiPlayer player = core()
                .getOptionalPlayerId(name)
                .orElseThrow(() -> new PlayerNotFoundException(name));
        authorization
                .bot(authorizationHeader)
                .needPermission("user.ban", player);
        player.ban(reason);
        return Response
                .ok()
                .build();
    }
}
