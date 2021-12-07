package com.github.jenya705.mcapi.module.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.jenya705.mcapi.*;
import com.github.jenya705.mcapi.block.Block;
import com.github.jenya705.mcapi.block.data.*;
import com.github.jenya705.mcapi.command.*;
import com.github.jenya705.mcapi.enchantment.Enchantment;
import com.github.jenya705.mcapi.enchantment.ItemEnchantment;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.entity.EntityEventTunnelAuthorizationRequest;
import com.github.jenya705.mcapi.entity.EntityLinkRequest;
import com.github.jenya705.mcapi.entity.EntitySubscribeRequest;
import com.github.jenya705.mcapi.entity.enchantment.EntityItemEnchantment;
import com.github.jenya705.mcapi.entity.inventory.EntityInventoryItemStack;
import com.github.jenya705.mcapi.entity.inventory.EntityItemStack;
import com.github.jenya705.mcapi.error.JsonDeserializeException;
import com.github.jenya705.mcapi.error.PlayerNotFoundException;
import com.github.jenya705.mcapi.event.*;
import com.github.jenya705.mcapi.form.FormComponent;
import com.github.jenya705.mcapi.form.FormPlatformProvider;
import com.github.jenya705.mcapi.form.component.ComponentMapParser;
import com.github.jenya705.mcapi.inventory.Inventory;
import com.github.jenya705.mcapi.inventory.InventoryItemStack;
import com.github.jenya705.mcapi.inventory.ItemStack;
import com.github.jenya705.mcapi.module.authorization.AuthorizationModule;
import com.github.jenya705.mcapi.module.command.ApiCommandDeserializer;
import com.github.jenya705.mcapi.module.command.CommandModule;
import com.github.jenya705.mcapi.module.database.DatabaseModule;
import com.github.jenya705.mcapi.module.enchantment.EnchantmentStorage;
import com.github.jenya705.mcapi.module.mapper.Mapper;
import com.github.jenya705.mcapi.module.material.MaterialStorage;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.player.PlayerAbilities;
import com.github.jenya705.mcapi.rest.*;
import com.github.jenya705.mcapi.rest.block.*;
import com.github.jenya705.mcapi.rest.command.*;
import com.github.jenya705.mcapi.rest.enchantment.RestEnchantment;
import com.github.jenya705.mcapi.rest.enchantment.RestItemEnchantment;
import com.github.jenya705.mcapi.rest.event.*;
import com.github.jenya705.mcapi.rest.inventory.RestInventory;
import com.github.jenya705.mcapi.rest.inventory.RestInventoryItemStack;
import com.github.jenya705.mcapi.rest.inventory.RestItemStack;
import com.github.jenya705.mcapi.rest.player.RestPlayer;
import com.github.jenya705.mcapi.rest.player.RestPlayerAbilities;
import com.github.jenya705.mcapi.world.World;
import net.kyori.adventure.text.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Bean
    private MaterialStorage materialStorage;

    @Bean
    private EnchantmentStorage enchantmentStorage;

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
                .tunnelJsonSerializer(Chest.class, RestChest::from)
                .tunnelJsonSerializer(Inventory.class, RestInventory::from)
                .tunnelJsonSerializer(Barrel.class, RestBarrel::from)
                .tunnelJsonSerializer(Furnace.class, RestFurnace::from)
                .tunnelJsonSerializer(Campfire.class, RestCampfire::from)
                .tunnelJsonSerializer(BrewingStand.class, RestBrewingStand::from)
                .tunnelJsonSerializer(EnderChest.class, RestEnderChest::from)
                .tunnelJsonSerializer(Stairs.class, RestStairs::from)
                .tunnelJsonSerializer(Slab.class, RestSlab::from)
                .tunnelJsonSerializer(PlayerAbilities.class, RestPlayerAbilities::from)
                .tunnelJsonSerializer(Enchantment.class, RestEnchantment::from)
                .tunnelJsonSerializer(ItemEnchantment.class, RestItemEnchantment::from)
                .tunnelJsonSerializer(ShulkerBox.class, RestShulkerBox::from)
                .tunnelJsonSerializer(RedstoneWire.class, RestRedstoneWire::from)
                .tunnelJsonSerializer(Door.class, RestDoor::from)
                .jsonDeserializer(Command.class, new ApiCommandDeserializer(commandModule))
                .jsonSerializer(Component.class, new ComponentSerializer())
                .tunnelJsonDeserializer(ItemStack.class, RestItemStack.class, this::parseItemStack)
                .tunnelJsonDeserializer(
                        InventoryItemStack.class,
                        RestInventoryItemStack.class,
                        rest -> new EntityInventoryItemStack(
                                rest.getIndex(),
                                parseItemStack(rest.getItem())
                        )
                )
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

    private ItemStack parseItemStack(RestItemStack rest) {
        return new EntityItemStack(
                materialStorage.getMaterial(rest.getMaterial()),
                rest.getAmount(),
                rest.getCustomName(),
                rest.getEnchantments() != null ?
                        rest
                                .getEnchantments()
                                .stream()
                                .map(enchantment -> new EntityItemEnchantment(
                                        enchantment.getLevel(),
                                        enchantmentStorage.getEnchantment(enchantment.getKey())
                                ))
                                .collect(Collectors.toList()) :
                        Collections.emptyList()
        );
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
