package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.command.CommandExecutor;
import com.github.jenya705.mcapi.util.PlayerUtils;

import java.io.File;
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

    void givePermission(ApiPlayer player, boolean toggled, String... permissions);

    Collection<? extends ApiPlayer> getPlayers();

    ApiPlayer getPlayer(String name);

    ApiPlayer getPlayer(UUID uuid);

    ApiOfflinePlayer getOfflinePlayer(String name);

    ApiOfflinePlayer getOfflinePlayer(UUID uuid);

    default Optional<? extends ApiPlayer> getOptionalPlayer(String name) {
        return Optional.ofNullable(getPlayer(name));
    }

    default Optional<? extends ApiPlayer> getOptionalPlayer(UUID uuid) {
        return Optional.ofNullable(getPlayer(uuid));
    }

    default Optional<? extends ApiPlayer> getOptionalPlayerId(String id) {
        return PlayerUtils.getPlayer(id, this);
    }

    default Optional<? extends ApiOfflinePlayer> getOptionalOfflinePlayer(String name) {
        return Optional.ofNullable(getOfflinePlayer(name));
    }

    default Optional<? extends ApiOfflinePlayer> getOptionalOfflinePlayer(UUID uuid) {
        return Optional.ofNullable(getOfflinePlayer(uuid));
    }

    default Optional<? extends ApiOfflinePlayer> getOptionalOfflinePlayerId(String id) {
        return PlayerUtils.getOfflinePlayer(id, this);
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

    File getPluginFile(String file);

    void disable();
}
