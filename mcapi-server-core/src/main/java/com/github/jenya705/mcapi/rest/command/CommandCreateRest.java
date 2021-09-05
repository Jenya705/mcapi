package com.github.jenya705.mcapi.rest.command;

import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.JerseyClass;
import com.github.jenya705.mcapi.command.ApiCommand;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.module.authorization.AuthorizationModule;
import com.github.jenya705.mcapi.module.command.CommandModule;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

/**
 * @author Jenya705
 */
@JerseyClass
@Path("/command")
public class CommandCreateRest implements BaseCommon {

    private final AuthorizationModule authorizationModule = bean(AuthorizationModule.class);
    private final CommandModule commandModule = bean(CommandModule.class);

    @POST
    public Response create(@HeaderParam("Authorization") String authorization, ApiCommand command) {
        AbstractBot bot = authorizationModule.bot(authorization);
        bot.needPermission("user.command.create");
        commandModule.registerCommand(command, bot.getEntity());
        return Response
                .noContent()
                .build();
    }

}
