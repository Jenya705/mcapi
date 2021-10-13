package com.github.jenya705.mcapi.mock;

import com.github.jenya705.mcapi.OfflinePlayer;
import com.github.jenya705.mcapi.Player;
import com.github.jenya705.mcapi.ServerCore;
import com.github.jenya705.mcapi.command.CommandExecutor;
import com.github.jenya705.mcapi.mock.filesystem.MockFileSystem;
import com.github.jenya705.mcapi.mock.filesystem.MockFileSystemImpl;

import java.io.IOException;
import java.util.*;

public class MockServerCore implements ServerCore {

    private final Map<UUID, Player> players = new HashMap<>();
    private final MockFileSystem fileSystem = new MockFileSystemImpl();

    public void addPlayer(Player player) {
        players.put(player.getUuid(), player);
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
        return fileSystem.getSpecific(file) != null || fileSystem.getConfig(file) != null;
    }

    @Override
    public String getAbsolutePath(String file) {
        return file;
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