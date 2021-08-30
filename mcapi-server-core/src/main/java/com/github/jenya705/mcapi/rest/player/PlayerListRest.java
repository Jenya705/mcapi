package com.github.jenya705.mcapi.rest.player;

import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.JerseyClass;
import com.github.jenya705.mcapi.module.authorization.AuthorizationModule;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
@JerseyClass
@Path("/player/list")
public class PlayerListRest implements BaseCommon {

    private final AuthorizationModule authorization = bean(AuthorizationModule.class);

    @Data
    @AllArgsConstructor(staticName = "of")
    static class PlayerList {
        private Collection<UUID> uuids;
    }

    @GET
    public Response getPlayers(@HeaderParam("Authorization") String authorizationHeader) {
        authorization
                .bot(authorizationHeader)
                .needPermission("user.list");
        return Response
                .ok()
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(PlayerList.of(
                        core()
                                .getPlayers()
                                .stream()
                                .map(ApiPlayer::getUuid)
                                .collect(Collectors.toList())
                ))
                .build();
    }
}
