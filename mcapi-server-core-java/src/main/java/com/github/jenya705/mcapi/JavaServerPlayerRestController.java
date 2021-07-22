package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.rest.JavaPlayerGetter;
import com.github.jenya705.mcapi.rest.JavaPlayerSendMessage;
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
    public JavaPlayer getPlayer(String name) {
        return (JavaPlayer) super.getPlayer(name);
    }

    @Override
    public JavaPlayer getPlayer(UUID uniqueId) {
        return (JavaPlayer) super.getPlayer(uniqueId);
    }

    @Override
    public void sendMessage(String name, Component message) {
        getPlayer(name).sendMessage(message);
    }

    @Override
    public void sendMessage(UUID uniqueId, Component message) {
        getPlayer(uniqueId).sendMessage(message);
    }

    @PUT
    @Path("/name/{name}/send/component")
    public Response sendMessageRest(@PathParam("name") String name, Component message) {
        sendMessage(name, message);
        return Response.ok().build();
    }

    @PUT
    @Path("/uuid/{uniqueId}/send/component")
    public Response sendMessageRest(@PathParam("uniqueId") UUID uniqueId, Component message) {
        sendMessage(uniqueId, message);
        return Response.ok().build();
    }

}
