package com.github.jenya705.mcapi.server.module.rest.route.command;

import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.module.command.CommandModule;
import com.github.jenya705.mcapi.server.module.rest.route.AbstractRouteHandler;
import com.github.jenya705.mcapi.server.module.web.Request;
import com.github.jenya705.mcapi.server.module.web.Response;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Jenya705
 */
@Singleton
public class DeleteCommandRouteHandler extends AbstractRouteHandler {

    private final CommandModule commandModule;

    @Inject
    public DeleteCommandRouteHandler(ServerApplication application, CommandModule commandModule) {
        super(application, Routes.COMMAND_DELETE);
        this.commandModule = commandModule;
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
