package com.github.jenya705.mcapi.rest;

import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.JerseyClass;
import jakarta.ws.rs.GET;
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

    @Data
    @AllArgsConstructor(staticName = "of")
    static class PlayerList {
        private Collection<UUID> uuids;
    }

    @GET
    public Response getPlayers() {
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
