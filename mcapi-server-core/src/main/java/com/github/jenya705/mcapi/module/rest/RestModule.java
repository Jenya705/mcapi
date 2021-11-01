package com.github.jenya705.mcapi.module.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.jenya705.mcapi.*;
import com.github.jenya705.mcapi.ApiError;
import com.github.jenya705.mcapi.block.Block;
import com.github.jenya705.mcapi.block.CommandBlock;
import com.github.jenya705.mcapi.command.*;
import com.github.jenya705.mcapi.entity.*;
import com.github.jenya705.mcapi.entity.api.EntityEventTunnelAuthorizationRequest;
import com.github.jenya705.mcapi.entity.api.EntityLinkRequest;
import com.github.jenya705.mcapi.entity.api.EntitySubscribeRequest;
import com.github.jenya705.mcapi.entity.command.*;
import com.github.jenya705.mcapi.entity.event.*;
import com.github.jenya705.mcapi.error.JsonDeserializeException;
import com.github.jenya705.mcapi.error.PlayerNotFoundException;
import com.github.jenya705.mcapi.event.*;
import com.github.jenya705.mcapi.form.FormComponent;
import com.github.jenya705.mcapi.form.FormPlatformProvider;
import com.github.jenya705.mcapi.form.component.ComponentMapParser;
import com.github.jenya705.mcapi.inventory.Inventory;
import com.github.jenya705.mcapi.inventory.ItemStack;
import com.github.jenya705.mcapi.module.authorization.AuthorizationModule;
import com.github.jenya705.mcapi.module.command.ApiCommandDeserializer;
import com.github.jenya705.mcapi.module.command.CommandModule;
import com.github.jenya705.mcapi.module.database.DatabaseModule;
import com.github.jenya705.mcapi.module.mapper.Mapper;
import com.github.jenya705.mcapi.world.World;

import java.util.Arrays;
import java.util.Map;

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

    @Bean
    private FormPlatformProvider formProvider;

    @Bean
    private ComponentMapParser formComponentParser;

    @OnStartup
    @SuppressWarnings("unchecked")
    public void start() {
        mapper
                .rawDeserializer(AbstractBot.class, authorization -> authorizationModule.bot(authorization))
                .rawDeserializer(Player.class, id ->
                        core()
                                .getOptionalPlayerId(id)
                                .orElseThrow(() -> PlayerNotFoundException.create(id))
                )
                .rawDeserializer(OfflinePlayer.class, id ->
                        core()
                                .getOptionalOfflinePlayerId(id)
                                .orElseThrow(() -> PlayerNotFoundException.create(id))
                )
                .throwableParser(JsonProcessingException.class, e -> JsonDeserializeException.create())
                .tunnelJsonSerializer(Command.class, RestCommand::from)
                .tunnelJsonSerializer(CommandExecutableOption.class, RestCommandExecutableOption::from)
                .tunnelJsonSerializer(CommandInteractionValue.class, RestCommandInteractionValue::from)
                .tunnelJsonSerializer(CommandOption.class, RestCommandOption::from)
                .tunnelJsonSerializer(CommandValueOption.class, RestCommandValueOption::from)
                .tunnelJsonSerializer(CommandInteractionEvent.class, RestCommandInteractionEvent::from)
                .tunnelJsonSerializer(JoinEvent.class, RestJoinEvent::from)
                .tunnelJsonSerializer(LinkEvent.class, RestLinkEvent::from)
                .tunnelJsonSerializer(MessageEvent.class, RestMessageEvent::from)
                .tunnelJsonSerializer(QuitEvent.class, RestQuitEvent::from)
                .tunnelJsonSerializer(SubscribeEvent.class, RestSubscribeEvent::from)
                .tunnelJsonSerializer(UnlinkEvent.class, RestUnlinkEvent::from)
                .tunnelJsonSerializer(CommandSender.class, RestCommandSender::from)
                .tunnelJsonSerializer(ApiError.class, RestError::from)
                .tunnelJsonSerializer(Form.class, RestForm::from)
                .tunnelJsonSerializer(LinkRequest.class, RestLinkRequest::from)
                .tunnelJsonSerializer(Location.class, RestLocation::from)
                .tunnelJsonSerializer(OfflinePlayer.class, RestOfflinePlayer::from)
                .tunnelJsonSerializer(Player.class, RestPlayer::from)
                .tunnelJsonSerializer(SubscribeRequest.class, RestSubscribeRequest::from)
                .tunnelJsonSerializer(EventTunnelAuthorizationRequest.class, RestEventTunnelAuthorizationRequest::from)
                .tunnelJsonSerializer(Permission.class, RestPermission::from)
                .tunnelJsonSerializer(World.class, RestWorld::from)
                .tunnelJsonSerializer(Block.class, RestBlock::from)
                .tunnelJsonSerializer(CommandBlock.class, RestCommandBlock::from)
                .tunnelJsonSerializer(ItemStack.class, RestItemStack::from)
                .tunnelJsonSerializer(Inventory.class, RestInventory::from)
                .jsonDeserializer(Command.class, new ApiCommandDeserializer(commandModule))
                .tunnelJsonDeserializer(
                        SubscribeRequest.class,
                        RestSubscribeRequest.class,
                        rest -> new EntitySubscribeRequest(rest.getSubscriptions())
                )
                .tunnelJsonDeserializer(
                        LinkRequest.class,
                        RestLinkRequest.class,
                        rest -> new EntityLinkRequest(
                                rest.getRequireRequestPermissions(),
                                rest.getOptionalRequestPermissions(),
                                rest.getMinecraftRequestCommands()
                        )
                )
                .tunnelJsonDeserializer(
                        EventTunnelAuthorizationRequest.class,
                        RestEventTunnelAuthorizationRequest.class,
                        rest -> new EntityEventTunnelAuthorizationRequest(
                                rest.getToken()
                        )
                )
                .tunnelJsonDeserializer(
                        com.github.jenya705.mcapi.form.Form.class,
                        RestForm.class,
                        this::parseForm
                )
                .tunnelJsonDeserializer(
                        com.github.jenya705.mcapi.form.Form.class,
                        Map[].class,
                        map -> parseForm(new RestForm(map))
                )
        ;
    }

    private com.github.jenya705.mcapi.form.Form parseForm(RestForm rest) {
        return formProvider
                .newBuilder()
                .components(
                        Arrays
                                .stream(rest.getComponents())
                                .map(formComponentParser::buildComponent)
                                .toArray(FormComponent[]::new)
                )
                .build();
    }
}
