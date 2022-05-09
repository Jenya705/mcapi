package com.github.jenya705.mcapi.server.module.rest.route.command;

import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.command.Command;
import com.github.jenya705.mcapi.permission.Permissions;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.github.jenya705.mcapi.server.module.command.CommandModule;
import com.github.jenya705.mcapi.server.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.server.module.web.Request;
import com.github.jenya705.mcapi.server.module.web.Response;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import reactor.core.publisher.Mono;

/**
 * @author Jenya705
 */
@Singleton
public class CreateCommandRouteHandler extends AbstractRouteHandler {

    private final CommandModule commandModule;

    @Inject
    public CreateCommandRouteHandler(ServerApplication application, CommandModule commandModule) {
        super(application, Routes.COMMAND_CREATE);
        this.commandModule = commandModule;
    }

    @Override
    public Mono<Response> handle(Request request) {
        AbstractBot bot = request.bot();
        bot.needPermission(Permissions.COMMAND_CREATE);
        Command command = request
                .bodyOrException(Command.class);
        commandModule.registerCommand(command, bot);
        return Mono.just(Response.create().noContent());
    }
}
