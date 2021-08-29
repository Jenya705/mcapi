package com.github.jenya705.mcapi.rest.offline;

import com.github.jenya705.mcapi.ApiOfflinePlayer;
import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.JerseyClass;
import com.github.jenya705.mcapi.error.PlayerNotFoundException;
import com.github.jenya705.mcapi.module.authorization.AuthorizationModule;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

/**
 * @author Jenya705
 */
@JerseyClass
@Path("/offline/{name}")
public class OfflinePlayerPunishmentRest implements BaseCommon {

    private final AuthorizationModule authorizationModule = bean(AuthorizationModule.class);

    @PATCH
    @Path("/ban")
    public Response ban(@PathParam("name") String name, @HeaderParam("Authorization") String authorization, String reason) {
        ApiOfflinePlayer player = core()
                .getOptionalOfflinePlayerId(name)
                .orElseThrow(() -> new PlayerNotFoundException(name));
        authorizationModule
                .bot(authorization)
                .needPermission("user.ban", player);
        player.ban(reason);
        return Response
                .ok()
                .build();
    }

}
