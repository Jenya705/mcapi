package com.github.jenya705.mcapi.rest.player;

import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.JerseyClass;
import com.github.jenya705.mcapi.error.PlayerNotFoundException;
import com.github.jenya705.mcapi.module.authorization.AuthorizationModule;
import jakarta.ws.rs.HeaderParam;
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

    private final AuthorizationModule authorization = bean(AuthorizationModule.class);

    @POST
    public Response sendMessage(
            @PathParam("name") String name,
            @HeaderParam("Authorization") String authorizationHeader,
            String message
    ) {
        ApiPlayer player = core()
                .getOptionalPlayerId(name)
                .orElseThrow(() -> new PlayerNotFoundException(name));
        authorization
                .bot(authorizationHeader)
                .needPermission("user.send_message", player);
        player.sendMessage(message);
        return Response
                .ok()
                .build();
    }
}
