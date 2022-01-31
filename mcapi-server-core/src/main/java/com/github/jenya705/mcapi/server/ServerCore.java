package com.github.jenya705.mcapi.server;

import com.github.jenya705.mcapi.Material;
import com.github.jenya705.mcapi.OfflinePlayer;
import com.github.jenya705.mcapi.entity.Entity;
import com.github.jenya705.mcapi.inventory.Inventory;
import com.github.jenya705.mcapi.inventory.InventoryView;
import com.github.jenya705.mcapi.menu.InventoryMenuView;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.server.application.FileType;
import com.github.jenya705.mcapi.server.command.CommandExecutor;
import com.github.jenya705.mcapi.server.util.PlayerUtils;
import com.github.jenya705.mcapi.world.World;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

    Entity getEntity(UUID uuid);

    World getWorld(String id);

    Collection<? extends Entity> getEntities();

    InventoryView createInventoryView(Inventory inventory, Material airMaterial, boolean unique);

    InventoryMenuView createInventoryMenuView(Inventory inventory, Material airMaterial, boolean unique);

    default InventoryView createInventoryView(Inventory inventory) {
        return createInventoryView(inventory, null);
    }

    default InventoryMenuView createInventoryMenuView(Inventory inventory) {
        return createInventoryMenuView(inventory, null);
    }

    default InventoryView createInventoryView(Inventory inventory, Material airMaterial) {
        return createInventoryView(inventory, airMaterial, true);
    }

    default InventoryMenuView createInventoryMenuView(Inventory inventory, Material airMaterial) {
        return createInventoryMenuView(inventory, airMaterial, true);
    }

    default Collection<? extends Entity> getEntities(Predicate<Entity> filter) {
        return getEntities()
                .stream()
                .filter(filter)
                .collect(Collectors.toList());
    }

    default Optional<? extends Player> getOptionalPlayer(String name) {
        return Optional.ofNullable(getPlayer(name));
    }

    default Optional<? extends Player> getOptionalPlayer(UUID uuid) {
        return Optional.ofNullable(getPlayer(uuid));
    }

    default Optional<? extends Player> getOptionalPlayerId(String id) {
        return PlayerUtils.getPlayer(id, this);
    }

    default Optional<? extends Entity> getOptionalEntity(UUID uuid) {
        return Optional.ofNullable(getEntity(uuid));
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

    default InputStream loadSpecificStream(String file) throws IOException {
        return new ByteArrayInputStream(loadSpecific(file));
    }

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

    Collection<String> getFilesInDirectory(String directory);

    boolean isExistsFile(String file);

    String getAbsolutePath(String file);

    void mkdirs(String file);

    void disable();
}
