package com.github.jenya705.mcapi.server.ss.model.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.jenya705.mcapi.Location;
import com.github.jenya705.mcapi.NamespacedKey;
import com.github.jenya705.mcapi.entity.player.EntityPlayerAbilities;
import com.github.jenya705.mcapi.entity.potion.EntityPotionEffect;
import com.github.jenya705.mcapi.inventory.Inventory;
import com.github.jenya705.mcapi.inventory.InventoryView;
import com.github.jenya705.mcapi.inventory.PlayerInventory;
import com.github.jenya705.mcapi.player.GameMode;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.player.PlayerAbilities;
import com.github.jenya705.mcapi.potion.PotionEffect;
import com.github.jenya705.mcapi.rest.player.RestPlayer;
import com.github.jenya705.mcapi.server.module.message.DefaultMessage;
import com.github.jenya705.mcapi.server.module.object.ObjectStorage;
import com.github.jenya705.mcapi.server.ss.SSConnection;
import com.github.jenya705.mcapi.server.ss.model.ModelEmpty;
import com.github.jenya705.mcapi.server.ss.model.ProxyModels;
import com.github.jenya705.mcapi.server.ss.model.json.ProxyJsonModel;
import com.github.jenya705.mcapi.server.util.LazyInitializer;
import net.kyori.adventure.text.Component;

import java.util.Collection;
import java.util.Locale;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
public class ProxyPlayer extends ProxyJsonModel implements Player {

    private final RestPlayer rest;
    private final SSConnection connection;
    private final ObjectStorage storage;

    private final Supplier<Collection<PotionEffect>> effects = new LazyInitializer<>(this::updateEffects);
    private final Supplier<PlayerAbilities> playerAbilities = new LazyInitializer<>(this::updatePlayerAbilities);

    public ProxyPlayer(JsonNode node, RestPlayer rest, SSConnection connection, ObjectStorage storage) {
        super(node);
        this.rest = rest;
        this.connection = connection;
        this.storage = storage;
    }

    @Override
    public void kick(String reason) {
        connection.sendModel(ProxyModels.KICK, new DefaultMessage(reason).type());
    }

    private Collection<PotionEffect> updateEffects() {
        return rest
                .getEffects()
                .stream()
                .map(rest -> new EntityPotionEffect(
                        storage.getPotionEffect(rest.getKey()),
                        rest.getAmplifier(),
                        rest.getDuration(),
                        rest.isAmbient(),
                        rest.isHidden()
                ))
                .collect(Collectors.toList());
    }

    private PlayerAbilities updatePlayerAbilities() {
        return new EntityPlayerAbilities(
                rest.getAbilities().isInvulnerable(),
                rest.getAbilities().isMayFly(),
                rest.getAbilities().isInstabuild(),
                rest.getAbilities().getWalkSpeed(),
                rest.getAbilities().isMayBuild(),
                rest.getAbilities().isFlying(),
                rest.getAbilities().getFlySpeed()
        );
    }

    @Override
    public Collection<PotionEffect> getEffects() {
        return effects.get();
    }

    @Override
    public float getHealth() {
        return rest.getHealth();
    }

    @Override
    public boolean hasAI() {
        return rest.isAi();
    }

    @Override
    public void kill() {
        connection.sendModel(ProxyModels.KILL, new ModelEmpty());
    }

    @Override
    public void chat(String message) {
        connection.sendModel(ProxyModels.CHAT, message);
    }

    @Override
    public void runCommand(String command) {
        connection.sendModel(ProxyModels.RUN_COMMAND, command);
    }

    @Override
    public PlayerInventory getInventory() {
        return null;
    }

    @Override
    public Inventory getEnderChest() {
        return null;
    }

    @Override
    public GameMode getGameMode() {
        return null;
    }

    @Override
    public PlayerAbilities getAbilities() {
        return playerAbilities.get();
    }

    @Override
    public Locale getLocale() {
        return Locale.forLanguageTag(rest.getLocale());
    }

    @Override
    public InventoryView openInventory(InventoryView inventory) {
        return null;
    }

    @Override
    public InventoryView openInventory(Inventory inventory) {
        return null;
    }

    @Override
    public void closeInventory() {

    }

    @Override
    public void sendMessage(String message) {

    }

    @Override
    public void sendMessage(Component component) {

    }

    @Override
    public boolean hasPermission(String permission) {
        return false;
    }

    @Override
    public UUID getUuid() {
        return null;
    }

    @Override
    public NamespacedKey getType() {
        return null;
    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public Component customName() {
        return rest.getCustomName();
    }

    @Override
    public String getName() {
        return rest.getName();
    }

    @Override
    public void ban(String reason) {

    }

    @Override
    public boolean isOnline() {
        return false;
    }
}
