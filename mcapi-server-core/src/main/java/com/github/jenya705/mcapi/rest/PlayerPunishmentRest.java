package com.github.jenya705.mcapi.rest;

import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.JerseyClass;
import com.github.jenya705.mcapi.error.PlayerNotFoundException;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

/**
 * @author Jenya705
 */
@JerseyClass
@Path("/player/{name}")
public class PlayerPunishmentRest implements BaseCommon {

    @DELETE
    @Path("/kick")
    public Response kick(@PathParam("name") String name, String reason) {
        core()
                .getOptionalPlayerId(name)
                .orElseThrow(() -> new PlayerNotFoundException(name))
                .kick(reason);
        return Response
                .ok()
                .build();
    }

    @DELETE
    @Path("/ban")
    public Response ban(@PathParam("name") String name, String reason) {
        core()
                .getOptionalPlayerId(name)
                .orElseThrow(() -> new PlayerNotFoundException(name))
                .ban(reason);
        return Response
                .ok()
                .build();
    }
}
