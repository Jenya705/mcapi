package com.github.jenya705.mcapi.rest;

import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.JerseyClass;
import com.github.jenya705.mcapi.error.BadUuidFormatException;
import com.github.jenya705.mcapi.module.authorization.AuthorizationModule;
import com.github.jenya705.mcapi.util.PlayerUtils;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

/**
 * @author Jenya705
 */
@JerseyClass
@Path("/bot/@me/permission/{permissionName}")
public class BotPermissionRest implements BaseCommon {

    private final AuthorizationModule authorizationModule = bean(AuthorizationModule.class);

    @Data
    @AllArgsConstructor(staticName = "of")
    static class HasPermission {
        private boolean toggled;
    }

    @GET
    public Response hasPermission(
            @PathParam("permissionName") String permissionName,
            @HeaderParam("Authorization") String authorization
    ) {
        return Response
                .ok()
                .entity(HasPermission.of(
                        authorizationModule
                                .bot(authorization)
                                .hasPermission(permissionName)
                ))
                .build();
    }

    @GET
    @Path("/target/{target}")
    public Response hasPermission(
            @PathParam("permissionName") String permissionName,
            @PathParam("target") String target,
            @HeaderParam("Authorization") String authorization
    ) {
        return Response
                .ok()
                .entity(HasPermission.of(
                        authorizationModule
                                .bot(authorization)
                                .hasPermission(
                                        permissionName,
                                        PlayerUtils
                                                .optionalUuid(target)
                                                .orElseThrow(() -> new BadUuidFormatException(target))
                                )
                ))
                .build();
    }
}
