package com.github.jenya705.mcapi.rest.offline;

import com.github.jenya705.mcapi.ApiOfflinePlayer;
import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.JerseyClass;
import com.github.jenya705.mcapi.error.PlayerNotFoundException;
import com.github.jenya705.mcapi.module.authorization.AuthorizationModule;
import com.github.jenya705.mcapi.util.RawUtils;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

/**
 * @author Jenya705
 */
@JerseyClass
@Path("/offline/{name}")
public class OfflinePlayerGetterRest implements BaseCommon {

    private final AuthorizationModule authorizationModule = bean(AuthorizationModule.class);

    @GET
    public Response getOfflinePlayer(@PathParam("name") String name, @HeaderParam("Authorization") String authorization) {
        ApiOfflinePlayer player = core()
                .getOptionalOfflinePlayerId(name)
                .orElseThrow(() -> new PlayerNotFoundException(name));
        authorizationModule
                .bot(authorization)
                .needPermission("user.get", player);
        return Response
                .ok()
                .entity(RawUtils.raw(player))
                .build();
    }
}
