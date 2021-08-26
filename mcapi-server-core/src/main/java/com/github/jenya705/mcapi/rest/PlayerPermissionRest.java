package com.github.jenya705.mcapi.rest;

import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.JerseyClass;
import com.github.jenya705.mcapi.error.PlayerNotFoundException;
import com.github.jenya705.mcapi.module.authorization.AuthorizationModule;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Jenya705
 */
@JerseyClass
@Path("/player/{name}/permission/{permissionName}")
public class PlayerPermissionRest implements BaseCommon {

    private final AuthorizationModule authorization = bean(AuthorizationModule.class);

    @Data
    @AllArgsConstructor(staticName = "of")
    static class HasPermission {
        private boolean toggled;
    }

    @GET
    public Response hasPermission(
            @PathParam("name") String name,
            @PathParam("permissionName") String permissionName,
            @HeaderParam("Authorization") String authorizationHeader
    ) {
        ApiPlayer player = core()
                .getOptionalPlayerId(name)
                .orElseThrow(() -> new PlayerNotFoundException(name));
        authorization
                .bot(authorizationHeader)
                .needPermission("user.has_permission", player);
        return Response
                .ok()
                .entity(HasPermission.of(
                        player.hasPermission(permissionName)
                ))
                .build();
    }
}
