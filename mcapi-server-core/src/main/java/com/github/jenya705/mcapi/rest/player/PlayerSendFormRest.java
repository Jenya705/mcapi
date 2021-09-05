package com.github.jenya705.mcapi.rest.player;

import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.JerseyClass;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.error.PlayerNotFoundException;
import com.github.jenya705.mcapi.form.Form;
import com.github.jenya705.mcapi.form.FormComponent;
import com.github.jenya705.mcapi.form.FormPlatformProvider;
import com.github.jenya705.mcapi.form.component.ComponentMapParser;
import com.github.jenya705.mcapi.form.entity.ApiFormEntity;
import com.github.jenya705.mcapi.module.authorization.AuthorizationModule;
import com.github.jenya705.mcapi.util.Selector;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

import java.util.Arrays;

/**
 * @author Jenya705
 */
@JerseyClass
@Path("/player/{name}/send/form")
public class PlayerSendFormRest implements BaseCommon {

    private final AuthorizationModule authorizationModule = bean(AuthorizationModule.class);
    private final FormPlatformProvider formProvider = bean(FormPlatformProvider.class);

    @POST
    public Response sendForm(@PathParam("name") String name, @HeaderParam("Authorization") String authorization, ApiFormEntity form) {
        AbstractBot bot = authorizationModule.bot(authorization);
        Selector<ApiPlayer> selector = core()
                .getPlayersBySelector(name, bot);
        if (selector.isEmpty()) {
            throw new PlayerNotFoundException(name);
        }
        bot.needPermission("user.send_message" + selector.getPermissionName(), selector.getTarget());
        Form serverForm = formProvider
                .newBuilder()
                .components(
                        Arrays.stream(form.getComponents())
                                .map(ComponentMapParser::buildComponent)
                                .toArray(FormComponent[]::new)
                )
                .build();
        selector.forEach(it -> formProvider.sendMessage(it, serverForm));
        return Response
                .noContent()
                .build();
    }
}
