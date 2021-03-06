package com.github.jenya705.mcapi.server.module.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.jenya705.mcapi.*;
import com.github.jenya705.mcapi.block.Block;
import com.github.jenya705.mcapi.block.data.*;
import com.github.jenya705.mcapi.command.CommandInteractionValue;
import com.github.jenya705.mcapi.enchantment.Enchantment;
import com.github.jenya705.mcapi.enchantment.ItemEnchantment;
import com.github.jenya705.mcapi.entity.*;
import com.github.jenya705.mcapi.entity.enchantment.EntityItemEnchantment;
import com.github.jenya705.mcapi.entity.inventory.EntityIdentifiedInventoryItemStack;
import com.github.jenya705.mcapi.entity.inventory.EntityInventoryItemStack;
import com.github.jenya705.mcapi.entity.inventory.EntityItemStack;
import com.github.jenya705.mcapi.error.*;
import com.github.jenya705.mcapi.event.*;
import com.github.jenya705.mcapi.inventory.*;
import com.github.jenya705.mcapi.menu.InventoryMenuView;
import com.github.jenya705.mcapi.player.OfflinePlayer;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.player.PlayerAbilities;
import com.github.jenya705.mcapi.player.PlayerID;
import com.github.jenya705.mcapi.potion.PotionEffect;
import com.github.jenya705.mcapi.potion.PotionEffectType;
import com.github.jenya705.mcapi.rest.*;
import com.github.jenya705.mcapi.rest.block.*;
import com.github.jenya705.mcapi.rest.command.RestCommandInteractionEvent;
import com.github.jenya705.mcapi.rest.command.RestCommandInteractionValue;
import com.github.jenya705.mcapi.rest.enchantment.RestEnchantment;
import com.github.jenya705.mcapi.rest.enchantment.RestItemEnchantment;
import com.github.jenya705.mcapi.rest.entity.RestCapturedEntityClickEvent;
import com.github.jenya705.mcapi.rest.entity.RestEntity;
import com.github.jenya705.mcapi.rest.entity.RestLivingEntity;
import com.github.jenya705.mcapi.rest.event.*;
import com.github.jenya705.mcapi.rest.inventory.*;
import com.github.jenya705.mcapi.rest.player.RestPlayer;
import com.github.jenya705.mcapi.rest.player.RestPlayerAbilities;
import com.github.jenya705.mcapi.rest.potion.RestPotionEffect;
import com.github.jenya705.mcapi.server.application.AbstractApplicationModule;
import com.github.jenya705.mcapi.server.application.OnStartup;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.github.jenya705.mcapi.server.form.FormComponent;
import com.github.jenya705.mcapi.server.form.FormPlatformProvider;
import com.github.jenya705.mcapi.server.form.component.ComponentMapParser;
import com.github.jenya705.mcapi.server.inventory.InventoryContainer;
import com.github.jenya705.mcapi.server.inventory.InventoryViewModel;
import com.github.jenya705.mcapi.server.module.authorization.AuthorizationModule;
import com.github.jenya705.mcapi.server.module.mapper.Mapper;
import com.github.jenya705.mcapi.server.module.object.ObjectStorage;
import com.github.jenya705.mcapi.server.util.PlayerUtils;
import com.github.jenya705.mcapi.world.World;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.kyori.adventure.text.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
@Singleton
public class RestModule extends AbstractApplicationModule {

    private final Mapper mapper;
    private final AuthorizationModule authorizationModule;
    private final FormPlatformProvider formProvider;
    private final ComponentMapParser formComponentParser;
    private final ObjectStorage objectStorage;

    @Inject
    public RestModule(ServerApplication application, Mapper mapper, AuthorizationModule authorizationModule,
                      FormPlatformProvider formProvider, ComponentMapParser formComponentParser,
                      ObjectStorage objectStorage) {
        super(application);
        this.mapper = mapper;
        this.authorizationModule = authorizationModule;
        this.formProvider = formProvider;
        this.formComponentParser = formComponentParser;
        this.objectStorage = objectStorage;
    }

    @OnStartup
    @SuppressWarnings("unchecked")
    public void start() {
        mapper
                .rawDeserializer(NamespacedKey.class, NamespacedKey::from)
                .rawDeserializer(AbstractBot.class, authorizationModule::bot)
                .rawDeserializer(UUID.class, this::getUuid)
                .rawDeserializer(PotionEffectType.class, objectStorage::getPotionEffect)
                .rawDeserializer(Material.class, objectStorage::getMaterial)
                .rawDeserializer(NamespacedKey.class, NamespacedKey::from)
                .rawDeserializer(PlayerID.class, PlayerUtils::parsePlayerId)
                .throwableParser(JsonProcessingException.class, e -> JsonDeserializeException.create())
                .tunnelJsonDeserializer(NamespacedKey.class, String.class, NamespacedKey::from)
                .tunnelJsonDeserializer(PlayerID.class, String.class, PlayerUtils::parsePlayerId)
                .tunnelJsonSerializer(PlayerID.class, PlayerID::toString)
                .tunnelJsonSerializer(PotionEffect.class, RestPotionEffect::from)
                .tunnelJsonSerializer(NamespacedKey.class, Object::toString)
                .tunnelJsonSerializer(CommandInteractionValue.class, RestCommandInteractionValue::from)
                .tunnelJsonSerializer(CommandInteractionEvent.class, RestCommandInteractionEvent::from)
                .tunnelJsonSerializer(JoinEvent.class, RestJoinEvent::from)
                .tunnelJsonSerializer(LinkEvent.class, RestLinkEvent::from)
                .tunnelJsonSerializer(MessageEvent.class, RestMessageEvent::from)
                .tunnelJsonSerializer(QuitEvent.class, RestQuitEvent::from)
                .tunnelJsonSerializer(SubscribeEvent.class, RestSubscribeEvent::from)
                .tunnelJsonSerializer(UnlinkEvent.class, RestUnlinkEvent::from)
                .tunnelJsonSerializer(InventoryMoveEvent.class, RestInventoryMoveEvent::from)
                .tunnelJsonSerializer(CommandSender.class, RestCommandSender::from)
                .tunnelJsonSerializer(ApiError.class, RestError::from)
                .tunnelJsonSerializer(Form.class, RestForm::from)
                .tunnelJsonSerializer(LinkRequest.class, RestLinkRequest::from)
                .tunnelJsonSerializer(Location.class, RestLocation::from)
                .tunnelJsonSerializer(OfflinePlayer.class, RestOfflinePlayer::from)
                .tunnelJsonSerializer(SubscribeRequest.class, RestSubscribeRequest::from)
                .tunnelJsonSerializer(EventTunnelAuthorizationRequest.class, RestEventTunnelAuthorizationRequest::from)
                .tunnelJsonSerializer(CapturedEntityClickEvent.class, RestCapturedEntityClickEvent::from)
                .tunnelJsonSerializer(Permission.class, RestPermission::from)
                .tunnelJsonSerializer(World.class, RestWorld::from)
                .tunnelJsonSerializer(Block.class, RestBlock::from)
                .tunnelJsonSerializer(CommandBlock.class, RestCommandBlock::from)
                .tunnelJsonSerializer(ItemStack.class, RestItemStack::from)
                .tunnelJsonSerializer(InventoryItemStack.class, RestInventoryItemStack::from)
                .tunnelJsonSerializer(IdentifiedInventoryItemStack.class, RestIdentifiedInventoryItemStack::from)
                .tunnelJsonSerializer(Chest.class, RestChest::from)
                .tunnelJsonSerializer(Inventory.class, RestInventory::from)
                .tunnelJsonSerializer(Barrel.class, RestBarrel::from)
                .tunnelJsonSerializer(Furnace.class, RestFurnace::from)
                .tunnelJsonSerializer(Campfire.class, RestCampfire::from)
                .tunnelJsonSerializer(BrewingStand.class, RestBrewingStand::from)
                .tunnelJsonSerializer(EnderChest.class, RestEnderChest::from)
                .tunnelJsonSerializer(Stairs.class, RestStairs::from)
                .tunnelJsonSerializer(Slab.class, RestSlab::from)
                .tunnelJsonSerializer(Beacon.class, RestBeacon::from)
                .tunnelJsonSerializer(Enchantment.class, RestEnchantment::from)
                .tunnelJsonSerializer(ItemEnchantment.class, RestItemEnchantment::from)
                .tunnelJsonSerializer(ShulkerBox.class, RestShulkerBox::from)
                .tunnelJsonSerializer(RedstoneWire.class, RestRedstoneWire::from)
                .tunnelJsonSerializer(Door.class, RestDoor::from)
                .tunnelJsonSerializer(BoundingBox.class, RestBoundingBox::from)
                .tunnelJsonSerializer(Piston.class, RestPiston::from)
                .tunnelJsonSerializer(Entity.class, RestEntity::from)
                .tunnelJsonSerializer(LivingEntity.class, RestLivingEntity::from)
                .tunnelJsonSerializer(Player.class, RestPlayer::from)
                .tunnelJsonSerializer(PlayerAbilities.class, RestPlayerAbilities::from)
                .tunnelJsonSerializer(EntitySpawnEvent.class, RestEntitySpawnEvent::from)
                .tunnelJsonSerializer(EntityDespawnEvent.class, RestEntityDespawnEvent::from)
                .jsonSerializer(Component.class, new ComponentSerializer())
                .tunnelJsonDeserializer(
                        InventoryViewModel.class,
                        RestInventoryView.class,
                        rest -> new InventoryViewModel(
                                objectStorage.getMaterial(rest.getAirMaterial()),
                                buildInventory(
                                        Arrays
                                                .stream(rest.getItems())
                                                .map(this::parseIdentifiedInventoryItemStack)
                                                .toArray(IdentifiedInventoryItemStack[]::new),
                                        rest.getSize()
                                )
                        )
                )
                .tunnelJsonDeserializer(ItemStack.class, RestItemStack.class, this::parseItemStack)
                .tunnelJsonDeserializer(
                        InventoryItemStack.class,
                        RestInventoryItemStack.class,
                        this::parseInventoryItemStack
                )
                .tunnelJsonDeserializer(
                        IdentifiedInventoryItemStack.class,
                        RestIdentifiedInventoryItemStack.class,
                        this::parseIdentifiedInventoryItemStack
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
                                Objects.requireNonNullElse(rest.getRequireRequestPermissions(), new String[0]),
                                Objects.requireNonNullElse(rest.getOptionalRequestPermissions(), new String[0]),
                                Objects.requireNonNullElse(rest.getMinecraftRequestCommands(), new String[0])
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
                        com.github.jenya705.mcapi.server.form.Form.class,
                        RestForm.class,
                        this::parseForm
                )
                .tunnelJsonDeserializer(
                        com.github.jenya705.mcapi.server.form.Form.class,
                        Map[].class,
                        map -> parseForm(new RestForm(map))
                )
        ;
    }

    private ItemStack parseItemStack(RestItemStack rest) {
        return new EntityItemStack(
                objectStorage.getMaterial(rest.getMaterial()),
                rest.getAmount(),
                rest.getCustomName(),
                rest.getEnchantments() != null ?
                        rest
                                .getEnchantments()
                                .stream()
                                .map(enchantment -> new EntityItemEnchantment(
                                        enchantment.getLevel(),
                                        objectStorage.getEnchantment(enchantment.getKey())
                                ))
                                .collect(Collectors.toList()) :
                        Collections.emptyList()
        );
    }

    private InventoryItemStack parseInventoryItemStack(RestInventoryItemStack rest) {
        return new EntityInventoryItemStack(
                rest.getIndex(),
                parseItemStack(rest.getItem())
        );
    }

    private IdentifiedInventoryItemStack parseIdentifiedInventoryItemStack(RestIdentifiedInventoryItemStack rest) {
        return new EntityIdentifiedInventoryItemStack(
                rest.getId(),
                new EntityInventoryItemStack(
                        rest.getIndex(),
                        parseItemStack(rest.getItem())
                )
        );
    }

    private com.github.jenya705.mcapi.server.form.Form parseForm(RestForm rest) {
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

    private UUID getUuid(String uuid) {
        return PlayerUtils
                .optionalUuid(uuid)
                .orElseThrow(() -> BadUuidFormatException.create(uuid));
    }

    private ItemStack[] buildInventory(IdentifiedInventoryItemStack[] items, int size) {
        ItemStack[] result = new ItemStack[size];
        for (IdentifiedInventoryItemStack inventoryItemStack : items) {
            result[inventoryItemStack.getIndex()] = inventoryItemStack;
        }
        return result;
    }
}
