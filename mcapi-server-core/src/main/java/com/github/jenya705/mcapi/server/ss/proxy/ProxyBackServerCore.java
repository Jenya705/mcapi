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
import lombok.Getter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 *
 * It is server core for back end server. It is not implementing IO operations and some others.
 *
 * @author Jenya705
 */
public class ProxyBackServerCore implements ServerCore {

    @Getter
    private final Connection connection;
    private final ProxyServerModule proxyModule;

    public ProxyBackServerCore(ProxyServerModule proxyModule, Connection connection) {
        this.proxyModule = proxyModule;
        this.connection = connection;
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

    @Override
    public Map<String, Object> loadConfig(String file) throws IOException {
        return proxyModule.core().loadConfig(file);
    }

    @Override
    public byte[] loadSpecific(String file) throws IOException {
        return proxyModule.core().loadSpecific(file);
    }

    @Override
    public void saveConfig(String file, Map<String, Object> config) throws IOException {
        proxyModule.core().saveConfig(file, config);
    }

    @Override
    public void saveSpecific(String file, byte[] bytes) throws IOException {
        proxyModule.core().saveSpecific(file, bytes);
    }

    @Override
    public Collection<String> getFilesInDirectory(String directory) {
        return proxyModule.core().getFilesInDirectory(directory);
    }

    @Override
    public boolean isExistsFile(String file) {
        return proxyModule.core().isExistsFile(file);
    }

    @Override
    public String getAbsolutePath(String file) {
        return proxyModule.core().getAbsolutePath(file);
    }

    @Override
    public void mkdirs(String file) {
        proxyModule.core().mkdirs(file);
    }

    @Override
    public void disable() {
        proxyModule.core().disable();
    }
}
