package com.github.jenya705.mcapi.mock;

import com.github.jenya705.mcapi.Material;
import com.github.jenya705.mcapi.player.OfflinePlayer;
import com.github.jenya705.mcapi.entity.Entity;
import com.github.jenya705.mcapi.inventory.Inventory;
import com.github.jenya705.mcapi.inventory.InventoryView;
import com.github.jenya705.mcapi.menu.InventoryMenuView;
import com.github.jenya705.mcapi.mock.filesystem.MockFileSystem;
import com.github.jenya705.mcapi.mock.filesystem.MockFileSystemImpl;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.server.ServerCore;
import com.github.jenya705.mcapi.server.command.CommandExecutor;
import com.github.jenya705.mcapi.world.World;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MockServerCore implements ServerCore {

    private final Map<UUID, Player> players = new HashMap<>();
    private final MockFileSystem fileSystem = new MockFileSystemImpl();
    private final Map<String, MockCommand> commands = new HashMap<>();

    public void addPlayer(Player player) {
        players.put(player.getUuid(), player);
    }

    @Override
    public void addCommand(String name, CommandExecutor executor, String permissionName) {
        commands.put(name, new MockCommand(permissionName, executor));
    }

    @Override
    public void permission(String name, boolean toggled) {

    }

    @Override
    public void givePermission(Player player, boolean toggled, String... permissions) {

    }

    @Override
    public Collection<? extends Player> getPlayers() {
        return players.values();
    }

    @Override
    public Player getPlayer(String name) {
        return players
                .values()
                .stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .findAny()
                .orElse(null);
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
    public World getWorld(String id) {
        return null;
    }

    @Override
    public Player getPlayer(UUID uuid) {
        return players.getOrDefault(uuid, null);
    }

    @Override
    public OfflinePlayer getOfflinePlayer(String name) {
        return getPlayer(name);
    }

    @Override
    public OfflinePlayer getOfflinePlayer(UUID uuid) {
        return getPlayer(uuid);
    }

    @Override
    public Entity getEntity(UUID uuid) {
        return null;
    }

    @Override
    public Collection<? extends Entity> getEntities() {
        return null;
    }

    @Override
    public Map<String, Object> loadConfig(String file) throws IOException {
        return fileSystem.getConfig(file);
    }

    @Override
    public byte[] loadSpecific(String file) throws IOException {
        return fileSystem.getSpecific(file);
    }

    @Override
    public void saveConfig(String file, Map<String, Object> config) throws IOException {
        fileSystem.createConfig(file, config);
    }

    @Override
    public void saveSpecific(String file, byte[] bytes) throws IOException {
        fileSystem.createFile(file, bytes);
    }

    @Override
    public boolean isExistsFile(String file) {
        return fileSystem.isExistsFile(file);
    }

    @Override
    public String getAbsolutePath(String file) {
        return file;
    }

    @Override
    public Collection<String> getFilesInDirectory(String directory) {
        return null;
    }

    @Override
    public void mkdirs(String file) {
        /* NOTHING */
    }

    @Override
    public void disable() {
        /* NOTHING */
    }
}
