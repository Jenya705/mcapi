package com.github.jenya705.mcapi.rest.link;

import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.JerseyClass;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.entity.RestLinkRequest;
import com.github.jenya705.mcapi.error.PlayerNotFoundException;
import com.github.jenya705.mcapi.module.authorization.AuthorizationModule;
import com.github.jenya705.mcapi.module.link.LinkingModule;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

/**
 * @author Jenya705
 */
@JerseyClass
@Path("/player/{name}/link")
public class LinkRequestRest implements BaseCommon {

    private final AuthorizationModule authorizationModule = bean(AuthorizationModule.class);
    private final LinkingModule linkingModule = bean(LinkingModule.class);

    @POST
    public Response linkRequest(@PathParam("name") String name, @HeaderParam("Authorization") String authorization, RestLinkRequest requestEntity) {
        ApiPlayer player = core()
                .getOptionalPlayerId(name)
                .orElseThrow(() -> new PlayerNotFoundException(name));
        AbstractBot bot = authorizationModule.bot(authorization);
        bot.needPermission("link.request", player.getUuid());
        linkingModule.requestLink(bot, player, requestEntity);
        return Response
                .noContent()
                .build();
    }
}
