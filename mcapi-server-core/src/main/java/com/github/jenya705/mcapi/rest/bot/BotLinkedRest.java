package com.github.jenya705.mcapi.rest.bot;

import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.JerseyClass;
import com.github.jenya705.mcapi.entity.BotLinkEntity;
import com.github.jenya705.mcapi.entity.PlayerListEntity;
import com.github.jenya705.mcapi.module.authorization.AuthorizationModule;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
@JerseyClass
@Path("/bot/@me/linked")
public class BotLinkedRest implements BaseCommon {

    private final AuthorizationModule authorizationModule = bean(AuthorizationModule.class);

    @GET
    public Response getLinked(@HeaderParam("Authorization") String authorization) {
        return Response
                .ok()
                .entity(PlayerListEntity.of(
                        authorizationModule
                                .bot(authorization)
                                .getLinks()
                                .stream()
                                .map(BotLinkEntity::getTarget)
                                .collect(Collectors.toList())
                ))
                .build();
    }
}
