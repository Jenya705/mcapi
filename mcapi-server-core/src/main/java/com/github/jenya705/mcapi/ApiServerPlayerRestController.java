package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.exception.ApiPlayerNotFoundException;
import com.github.jenya705.mcapi.permission.ApiServerPermissionUtils;
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
    public ApiPlayer getPlayer(@PathParam("name") String name, @HeaderParam("token") String token) {
        ApiPlayer player = getPlayer(name);
        ApiServerPermissionUtils.exceptionPermission(DefaultApiPermission.USER_GET, player.getUniqueId().toString(), token);
        return player;
    }

    @GET
    @Path("/uuid/{uniqueId}")
    @Override
    public ApiPlayer getPlayer(@PathParam("uniqueId") UUID uniqueId, @HeaderParam("token") String token) {
        ApiPlayer player = getPlayer(uniqueId);
        ApiServerPermissionUtils.exceptionPermission(DefaultApiPermission.USER_GET, uniqueId.toString(), token);
        return player;
    }

    @PUT
    @Path("/name/{name}/send")
    public Response sendMessageRest(@PathParam("name") String name, String message, @HeaderParam("token") String token) {
        sendMessage(name, message, token);
        return Response.ok().build();
    }

    @PUT
    @Path("/uuid/{uniqueId}/send")
    public Response sendMessageRest(@PathParam("uniqueId") UUID uniqueId, String message, @HeaderParam("token") String token) {
        sendMessage(uniqueId, message, token);
        return Response.ok().build();
    }

    @Override
    public void sendMessage(String name, String message, String token) {
        ApiPlayer player = getPlayer(name);
        ApiServerPermissionUtils.exceptionPermission(DefaultApiPermission.USER_SEND_MESSAGE, player.getUniqueId().toString(), token);
        player.sendMessage(message);
    }

    @Override
    public void sendMessage(UUID uniqueId, String message, String token) {
        ApiPlayer player = getPlayer(uniqueId);
        ApiServerPermissionUtils.exceptionPermission(DefaultApiPermission.USER_SEND_MESSAGE, uniqueId.toString(), token);
        player.sendMessage(message);
    }

    protected ApiPlayer exceptionPlayer(ApiPlayer player, String id) {
        if (player == null) throw new ApiPlayerNotFoundException(id);
        return player;
    }

    protected ApiPlayer getPlayer(String name) {
        return exceptionPlayer(ApiServerApplication.getApplication().getCore().getPlayer(name), name);
    }

    protected ApiPlayer getPlayer(UUID uniqueId) {
        return exceptionPlayer(ApiServerApplication.getApplication().getCore().getPlayer(uniqueId), uniqueId.toString());
    }

}
