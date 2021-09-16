package com.github.jenya705.mcapi.rest.command;

import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.JerseyClass;
import com.github.jenya705.mcapi.command.ApiCommandOption;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.entity.api.command.EntityCommand;
import com.github.jenya705.mcapi.entity.api.command.EntityCommandOption;
import com.github.jenya705.mcapi.entity.api.command.EntityCommandValueOption;
import com.github.jenya705.mcapi.entity.command.RestCommand;
import com.github.jenya705.mcapi.module.authorization.AuthorizationModule;
import com.github.jenya705.mcapi.module.command.CommandModule;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.util.Arrays;

/**
 * @author Jenya705
 */
@JerseyClass
@Path("/command")
public class CommandCreateRest implements BaseCommon {

    private final AuthorizationModule authorizationModule = bean(AuthorizationModule.class);
    private final CommandModule commandModule = bean(CommandModule.class);

    @POST
    public Response create(@HeaderParam("Authorization") String authorization, RestCommand command) {
        AbstractBot bot = authorizationModule.bot(authorization);
        bot.needPermission("user.command.create");
        commandModule.registerCommand(
                new EntityCommand(
                        command.getName(),
                        Arrays
                                .stream(command.getOptions())
                                .map(option -> new EntityCommandOption(option.getName(), option.getType()))
                                .toArray(ApiCommandOption[]::new)
                ),
                bot
        );
        return Response
                .noContent()
                .build();
    }
}
