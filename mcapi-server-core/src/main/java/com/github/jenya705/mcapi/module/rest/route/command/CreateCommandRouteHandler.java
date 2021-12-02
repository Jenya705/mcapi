package com.github.jenya705.mcapi.module.rest.route.command;

import com.github.jenya705.mcapi.Bean;
import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.command.Command;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.module.command.CommandModule;
import com.github.jenya705.mcapi.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.module.web.Request;
import com.github.jenya705.mcapi.module.web.Response;
import com.github.jenya705.mcapi.permission.Permissions;

/**
 * @author Jenya705
 */
public class CreateCommandRouteHandler extends AbstractRouteHandler {

    @Bean
    private CommandModule commandModule;

    public CreateCommandRouteHandler() {
        super(Routes.COMMAND_CREATE);
    }

    @Override
    public void handle(Request request, Response response) {
        AbstractBot bot = request.bot();
        bot.needPermission(Permissions.COMMAND_CREATE);
        Command command = request
                .bodyOrException(Command.class);
        commandModule.registerCommand(command, bot);
        response.noContent();
    }
}
