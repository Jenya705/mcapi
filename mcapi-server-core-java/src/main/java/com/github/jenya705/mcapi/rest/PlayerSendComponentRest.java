package com.github.jenya705.mcapi.rest;

import com.github.jenya705.mcapi.JavaBaseCommon;
import com.github.jenya705.mcapi.JavaPlayer;
import com.github.jenya705.mcapi.JerseyClass;
import com.github.jenya705.mcapi.error.PlayerNotFoundException;
import com.github.jenya705.mcapi.module.authorization.AuthorizationModule;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

/**
 * @author Jenya705
 */
@JerseyClass
@Path("/player/{name}/send/raw")
public class PlayerSendComponentRest implements JavaBaseCommon {

    private static final GsonComponentSerializer componentSerializer = GsonComponentSerializer.gson();

    private final AuthorizationModule authorization = bean(AuthorizationModule.class);

    @POST
    public Response sendMessage(
            @PathParam("name") String name,
            @HeaderParam("Authorization") String authorizationHeader,
            String message
    ) {
        JavaPlayer player = core()
                .getOptionalJavaPlayerId(name)
                .orElseThrow(() -> new PlayerNotFoundException(name));
        authorization
                .bot(authorizationHeader)
                .needPermission("user.send_message", player);
        player.sendMessage(componentSerializer.deserialize(message));
        return Response
                .ok()
                .build();
    }
}
