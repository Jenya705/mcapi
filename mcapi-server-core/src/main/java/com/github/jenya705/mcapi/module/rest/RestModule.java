package com.github.jenya705.mcapi.module.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.jenya705.mcapi.*;
import com.github.jenya705.mcapi.command.*;
import com.github.jenya705.mcapi.entity.*;
import com.github.jenya705.mcapi.entity.api.EntityGatewayAuthorizationRequest;
import com.github.jenya705.mcapi.entity.api.EntityLinkRequest;
import com.github.jenya705.mcapi.entity.api.EntitySubscribeRequest;
import com.github.jenya705.mcapi.entity.command.*;
import com.github.jenya705.mcapi.entity.event.*;
import com.github.jenya705.mcapi.error.JsonDeserializeException;
import com.github.jenya705.mcapi.error.PlayerNotFoundException;
import com.github.jenya705.mcapi.event.*;
import com.github.jenya705.mcapi.module.authorization.AuthorizationModule;
import com.github.jenya705.mcapi.module.command.ApiCommandDeserializer;
import com.github.jenya705.mcapi.module.command.CommandModule;
import com.github.jenya705.mcapi.module.database.DatabaseModule;
import com.github.jenya705.mcapi.module.mapper.Mapper;

/**
 * @author Jenya705
 */
public class RestModule extends AbstractApplicationModule {

    @Bean
    private Mapper mapper;

    @Bean
    private AuthorizationModule authorizationModule;

    @Bean
    private DatabaseModule databaseModule;

    @Bean
    private CommandModule commandModule;

    @OnStartup
    public void start() {
        mapper
                .rawDeserializer(AbstractBot.class, authorization -> authorizationModule.bot(authorization))
                .rawDeserializer(ApiPlayer.class, id ->
                        core()
                                .getOptionalPlayerId(id)
                                .orElseThrow(() -> new PlayerNotFoundException(id))
                )
                .throwableParser(JsonProcessingException.class, e -> new JsonDeserializeException())
                .tunnelJsonSerializer(ApiCommand.class, RestCommand::from)
                .tunnelJsonSerializer(ApiCommandExecutableOption.class, RestCommandExecutableOption::from)
                .tunnelJsonSerializer(ApiCommandInteractionValue.class, RestCommandInteractionValue::from)
                .tunnelJsonSerializer(ApiCommandOption.class, RestCommandOption::from)
                .tunnelJsonSerializer(ApiCommandValueOption.class, RestCommandValueOption::from)
                .tunnelJsonSerializer(CommandInteractionEvent.class, RestCommandInteractionEvent::from)
                .tunnelJsonSerializer(JoinEvent.class, RestJoinEvent::from)
                .tunnelJsonSerializer(LinkEvent.class, RestLinkEvent::from)
                .tunnelJsonSerializer(MessageEvent.class, RestMessageEvent::from)
                .tunnelJsonSerializer(QuitEvent.class, RestQuitEvent::from)
                .tunnelJsonSerializer(SubscribeEvent.class, RestSubscribeEvent::from)
                .tunnelJsonSerializer(UnlinkEvent.class, RestUnlinkEvent::from)
                .tunnelJsonSerializer(ApiCommandSender.class, RestCommandSender::from)
                .tunnelJsonSerializer(ApiError.class, RestError::from)
                .tunnelJsonSerializer(ApiForm.class, RestForm::from)
                .tunnelJsonSerializer(ApiLinkRequest.class, RestLinkRequest::from)
                .tunnelJsonSerializer(ApiLocation.class, RestLocation::from)
                .tunnelJsonSerializer(ApiOfflinePlayer.class, RestOfflinePlayer::from)
                .tunnelJsonSerializer(ApiPlayer.class, RestPlayer::from)
                .tunnelJsonSerializer(ApiSubscribeRequest.class, RestSubscribeRequest::from)
                .tunnelJsonSerializer(ApiGatewayAuthorizationRequest.class, RestGatewayAuthorizationRequest::from)
                .jsonDeserializer(ApiCommand.class, new ApiCommandDeserializer(commandModule))
                .tunnelJsonDeserializer(
                        ApiSubscribeRequest.class,
                        RestSubscribeRequest.class,
                        rest -> new EntitySubscribeRequest(rest.getSubscriptions())
                )
                .tunnelJsonDeserializer(
                        ApiLinkRequest.class,
                        RestLinkRequest.class,
                        rest -> new EntityLinkRequest(
                                rest.getRequireRequestPermissions(),
                                rest.getOptionalRequestPermissions(),
                                rest.getMinecraftRequestCommands()
                        )
                )
                .tunnelJsonDeserializer(
                        ApiGatewayAuthorizationRequest.class,
                        RestGatewayAuthorizationRequest.class,
                        rest -> new EntityGatewayAuthorizationRequest(
                                rest.getToken()
                        )
                )
        ;
    }
}
