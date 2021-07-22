package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.exception.ApiPlayerNotFoundException;
import com.github.jenya705.mcapi.rest.ApiPlayerGetter;
import com.github.jenya705.mcapi.rest.ApiPlayerSendMessage;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

/**
 *
 * Rest controller for player
 *
 * @since 1.0
 * @author Jenya705
 */
@Path("/player")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ApiServerPlayerRestController implements ApiPlayerGetter, ApiPlayerSendMessage {

    @GET
    @Path("/name/{name}")
    @Override
    public ApiPlayer getPlayer(@PathParam("name") String name) {
        return getPlayer(ApiServerApplication.getApplication().getCore().getPlayer(name), name);
    }

    @GET
    @Path("/uuid/{uniqueId}")
    @Override
    public ApiPlayer getPlayer(@PathParam("uniqueId") UUID uniqueId) {
        return getPlayer(ApiServerApplication.getApplication().getCore().getPlayer(uniqueId), uniqueId.toString());
    }

    @PUT
    @Path("/name/{name}/send")
    public Response sendMessageRest(@PathParam("name") String name, String message) {
        sendMessage(name, message);
        return Response.ok().build();
    }

    @PUT
    @Path("/uuid/{uniqueId}/send")
    public Response sendMessageRest(@PathParam("uniqueId") UUID uniqueId, String message) {
        sendMessage(uniqueId, message);
        return Response.ok().build();
    }

    @Override
    public void sendMessage(String name, String message) {
        getPlayer(name).sendMessage(message);
    }

    @Override
    public void sendMessage(UUID uniqueId, String message) {
        getPlayer(uniqueId).sendMessage(message);
    }

    protected ApiPlayer getPlayer(ApiPlayer player, String id) {
        if (player == null) throw new ApiPlayerNotFoundException(id);
        return player;
    }

}
