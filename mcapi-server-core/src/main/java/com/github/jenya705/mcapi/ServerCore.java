package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.block.Block;
import com.github.jenya705.mcapi.block.BlockData;
import com.github.jenya705.mcapi.command.CommandExecutor;
import com.github.jenya705.mcapi.util.PlayerUtils;
import com.github.jenya705.mcapi.world.World;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Jenya705
 */
public interface ServerCore {

    void addCommand(String name, CommandExecutor executor, String permissionName);

    void permission(String name, boolean toggled);

    void givePermission(Player player, boolean toggled, String... permissions);

    Collection<? extends Player> getPlayers();

    Player getPlayer(String name);

    Player getPlayer(UUID uuid);

    OfflinePlayer getOfflinePlayer(String name);

    OfflinePlayer getOfflinePlayer(UUID uuid);

    World getWorld(String id);

    default Optional<? extends Player> getOptionalPlayer(String name) {
        return Optional.ofNullable(getPlayer(name));
    }

    default Optional<? extends Player> getOptionalPlayer(UUID uuid) {
        return Optional.ofNullable(getPlayer(uuid));
    }

    default Optional<? extends Player> getOptionalPlayerId(String id) {
        return PlayerUtils.getPlayer(id, this);
    }

    default Optional<? extends OfflinePlayer> getOptionalOfflinePlayer(String name) {
        return Optional.ofNullable(getOfflinePlayer(name));
    }

    default Optional<? extends OfflinePlayer> getOptionalOfflinePlayer(UUID uuid) {
        return Optional.ofNullable(getOfflinePlayer(uuid));
    }

    default Optional<? extends OfflinePlayer> getOptionalOfflinePlayerId(String id) {
        return PlayerUtils.getOfflinePlayer(id, this);
    }

    default Optional<? extends World> getOptionalWorld(String id) {
        return Optional.ofNullable(getWorld(id));
    }

    default Object loadFile(String file, FileType type) throws IOException {
        if (type == FileType.CONFIG) {
            return loadConfig(file);
        }
        return loadSpecific(file);
    }

    Map<String, Object> loadConfig(String file) throws IOException;

    byte[] loadSpecific(String file) throws IOException;

    @SuppressWarnings("unchecked")
    default void saveFile(String file, FileType type, Object content) throws IOException {
        if (type == FileType.CONFIG) {
            saveConfig(file, (Map<String, Object>) content);
            return;
        }
        saveSpecific(file, (byte[]) content);
    }

    void saveConfig(String file, Map<String, Object> config) throws IOException;

    void saveSpecific(String file, byte[] bytes) throws IOException;

    boolean isExistsFile(String file);

    String getAbsolutePath(String file);

    void mkdirs(String file);

    void disable();
}
