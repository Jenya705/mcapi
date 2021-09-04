package com.github.jenya705.mcapi.rest;

import com.github.jenya705.mcapi.JavaBaseCommon;
import com.github.jenya705.mcapi.JavaPlayer;
import com.github.jenya705.mcapi.JerseyClass;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.error.PlayerNotFoundException;
import com.github.jenya705.mcapi.module.authorization.AuthorizationModule;
import com.github.jenya705.mcapi.util.Selector;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

/**
 * @author Jenya705
 */
@JerseyClass
@Path("/player/{name}/send/raw")
public class PlayerSendComponentRest implements JavaBaseCommon {

    public static final GsonComponentSerializer componentSerializer = GsonComponentSerializer.gson();

    private final AuthorizationModule authorization = bean(AuthorizationModule.class);

    @POST
    public Response sendMessage(
            @PathParam("name") String name,
            @HeaderParam("Authorization") String authorizationHeader,
            String message
    ) {
        AbstractBot bot = authorization.bot(authorizationHeader);
        Selector<JavaPlayer> selector = core()
                .getJavaPlayersBySelector(name, bot);
        if (selector.isEmpty()) {
            throw new PlayerNotFoundException(name);
        }
        bot.needPermission("user.kick" + selector.getPermissionName(), selector.getTarget());
        Component component = componentSerializer.deserialize(message);
        selector.forEach(player -> player.sendMessage(component));
        return Response
                .noContent()
                .build();
    }
}
