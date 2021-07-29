package com.github.jenya705.mcapi.module.web.rest;

import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.BaseServerCommon;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.UUID;

/**
 * @author Jenya705
 */
@Path("/player")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public final class ServerPlayerRest extends BaseServerCommon {

    @GET
    @Path("/name/{name}")
    public ApiPlayer getPlayerRest(@PathParam("name") String name) {
        return core().getPlayer(name);
    }

    @GET
    @Path("/uuid/{uniqueId}")
    public ApiPlayer getPlayerRest(@PathParam("uniqueId") UUID uniqueId) {
        return core().getPlayer(uniqueId);
    }

}
