package com.github.jenya705.mcapi.server.module.rest.route.command;

import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.server.Bean;
import com.github.jenya705.mcapi.server.module.command.CommandModule;
import com.github.jenya705.mcapi.server.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.server.module.web.Request;
import com.github.jenya705.mcapi.server.module.web.Response;

/**
 * @author Jenya705
 */
public class DeleteCommandRouteHandler extends AbstractRouteHandler {

    @Bean
    private CommandModule commandModule;

    public DeleteCommandRouteHandler() {
        super(Routes.COMMAND_DELETE);
    }

    @Override
    public void handle(Request request, Response response) throws Exception {
        commandModule.deleteCommand(
                request.paramOrException("name"),
                request.bot()
        );
        response.noContent();
    }
}
