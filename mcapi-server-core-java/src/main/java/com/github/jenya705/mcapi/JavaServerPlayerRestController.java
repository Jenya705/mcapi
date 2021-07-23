package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.permission.ApiServerPermissionUtils;
import com.github.jenya705.mcapi.rest.JavaPlayerGetter;
import com.github.jenya705.mcapi.rest.JavaPlayerSendMessage;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

import java.util.UUID;

/**
 *
 * @since 1.0
 * @author Jenya705
 */
public class JavaServerPlayerRestController extends ApiServerPlayerRestController implements JavaPlayerSendMessage, JavaPlayerGetter {

    private static final GsonComponentSerializer componentSerializer = GsonComponentSerializer.gson();

    @Override
    public JavaPlayer getPlayer(String name, String token) {
        return (JavaPlayer) super.getPlayer(name, token);
    }

    @Override
    public JavaPlayer getPlayer(UUID uniqueId, String token) {
        return (JavaPlayer) super.getPlayer(uniqueId, token);
    }

    @Override
    public void sendMessage(String name, Component message, String token) {
        JavaPlayer player = getPlayer(name);
        ApiServerPermissionUtils.exceptionPermission(DefaultApiPermission.USER_SEND_MESSAGE, player.getUniqueId().toString(), token);
        player.sendMessage(message);
    }

    @Override
    public void sendMessage(UUID uniqueId, Component message, String token) {
        JavaPlayer player = getPlayer(uniqueId);
        ApiServerPermissionUtils.exceptionPermission(DefaultApiPermission.USER_SEND_MESSAGE, uniqueId.toString(), token);
        player.sendMessage(message);
    }

    @PUT
    @Path("/name/{name}/send/component")
    public Response sendMessageRest(@PathParam("name") String name, Component message, @HeaderParam("token") String token) {
        sendMessage(name, message, token);
        return Response.ok().build();
    }

    @PUT
    @Path("/uuid/{uniqueId}/send/component")
    public Response sendMessageRest(@PathParam("uniqueId") UUID uniqueId, Component message, @HeaderParam("token") String token) {
        sendMessage(uniqueId, message, token);
        return Response.ok().build();
    }

    @Override
    protected JavaPlayer getPlayer(String name) {
        return (JavaPlayer) super.getPlayer(name);
    }

    @Override
    protected JavaPlayer getPlayer(UUID uniqueId) {
        return (JavaPlayer) super.getPlayer(uniqueId);
    }
}
