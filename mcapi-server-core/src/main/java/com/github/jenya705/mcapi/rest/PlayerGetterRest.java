package com.github.jenya705.mcapi.rest;

import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.JerseyClass;
import com.github.jenya705.mcapi.error.PlayerNotFoundException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Jenya705
 */
@Slf4j
@JerseyClass
@Path("/player/{name}")
public class PlayerGetterRest implements BaseCommon {

    @GET
    public Response getPlayer(@PathParam("name") String name) {
        return Response
                .ok()
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(core()
                        .getOptionalPlayerId(name)
                        .orElseThrow(() -> new PlayerNotFoundException(name))
                )
                .build();
    }

}
