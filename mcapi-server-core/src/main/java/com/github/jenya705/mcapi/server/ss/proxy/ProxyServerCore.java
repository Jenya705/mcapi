package com.github.jenya705.mcapi.server.ss.proxy;

import com.github.jenya705.mcapi.Material;
import com.github.jenya705.mcapi.NamespacedKey;
import com.github.jenya705.mcapi.entity.Entity;
import com.github.jenya705.mcapi.inventory.Inventory;
import com.github.jenya705.mcapi.inventory.InventoryView;
import com.github.jenya705.mcapi.menu.InventoryMenuView;
import com.github.jenya705.mcapi.player.OfflinePlayer;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.server.ServerCore;
import com.github.jenya705.mcapi.server.command.CommandExecutor;
import com.github.jenya705.mcapi.world.World;
import com.google.inject.Provider;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * @author Jenya705
 */
public abstract class ProxyServerCore implements ServerCore {

    private final Provider<ProxyServerModule> proxyServerProvider;

    public ProxyServerCore(Provider<ProxyServerModule> proxyServerProvider) {
        this.proxyServerProvider = proxyServerProvider;
    }

    protected ProxyServerModule getProxyServer() {
        return proxyServerProvider.get();
    }

    @Override
    public void addCommand(String name, CommandExecutor executor, String permissionName) {

    }

    @Override
    public void permission(String name, boolean toggled) {

    }

    @Override
    public void givePermission(Player player, boolean toggled, String... permissions) {

    }

    @Override
    public Flux<? extends Player> getPlayers() {
        return null;
    }

    @Override
    public Mono<Player> getPlayer(String name) {
        return null;
    }

    @Override
    public Mono<Player> getPlayer(UUID uuid) {
        return null;
    }

    @Override
    public Mono<OfflinePlayer> getOfflinePlayer(String name) {
        return null;
    }

    @Override
    public Mono<OfflinePlayer> getOfflinePlayer(UUID uuid) {
        return null;
    }

    @Override
    public Mono<Entity> getEntity(UUID uuid) {
        return null;
    }

    @Override
    public Mono<World> getWorld(NamespacedKey id) {
        return null;
    }

    @Override
    public Flux<? extends World> getWorlds() {
        return null;
    }

    @Override
    public Flux<? extends Entity> getEntities() {
        return null;
    }

    @Override
    public InventoryView createInventoryView(Inventory inventory, Material airMaterial, boolean unique) {
        return null;
    }

    @Override
    public InventoryMenuView createInventoryMenuView(Inventory inventory, Material airMaterial, boolean unique) {
        return null;
    }

}
