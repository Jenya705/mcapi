package com.github.jenya705.mcapi.rest;

import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.JerseyClass;
import com.github.jenya705.mcapi.error.PlayerNotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

/**
 * @author Jenya705
 */
@Path("/player/{name}/send")
@JerseyClass
public class PlayerSendMessageRest implements BaseCommon {

    @POST
    public Response sendMessage(@PathParam("name") String name, String message) {
        core()
                .getOptionalPlayerId(name)
                .orElseThrow(() -> new PlayerNotFoundException(name))
                .sendMessage(message);
        return Response
                .ok()
                .build();
    }
}
