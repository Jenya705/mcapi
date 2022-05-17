package com.github.jenya705.mcapi.server;

import com.github.jenya705.mcapi.Material;
import com.github.jenya705.mcapi.NamespacedKey;
import com.github.jenya705.mcapi.entity.CapturableEntity;
import com.github.jenya705.mcapi.entity.Entity;
import com.github.jenya705.mcapi.error.EntityNotCapturableException;
import com.github.jenya705.mcapi.inventory.Inventory;
import com.github.jenya705.mcapi.inventory.InventoryView;
import com.github.jenya705.mcapi.menu.InventoryMenuView;
import com.github.jenya705.mcapi.player.OfflinePlayer;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.server.application.FileType;
import com.github.jenya705.mcapi.server.command.CommandExecutor;
import com.github.jenya705.mcapi.server.util.PlayerUtils;
import com.github.jenya705.mcapi.world.World;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * @author Jenya705
 */
public interface ServerCore {

    void addCommand(String name, CommandExecutor executor, String permissionName);

    void permission(String name, boolean toggled);

    void givePermission(Player player, boolean toggled, String... permissions);

    Flux<? extends Player> getPlayers();

    Mono<Player> getPlayer(String name);

    Mono<Player> getPlayer(UUID uuid);

    Mono<OfflinePlayer> getOfflinePlayer(String name);

    Mono<OfflinePlayer> getOfflinePlayer(UUID uuid);

    Mono<Entity> getEntity(UUID uuid);

    Mono<World> getWorld(NamespacedKey id);

    Flux<? extends World> getWorlds();

    Flux<? extends Entity> getEntities();

    Mono<InventoryView> createInventoryView(Inventory inventory, Material airMaterial, boolean unique);

    Mono<InventoryMenuView> createInventoryMenuView(Inventory inventory, Material airMaterial, boolean unique);

    default Mono<Player> getPlayerById(String id) {
        return PlayerUtils.getPlayer(id, this);
    }

    default Mono<OfflinePlayer> getOfflinePlayerById(String id) {
        return PlayerUtils.getOfflinePlayer(id, this);
    }

    default Mono<CapturableEntity> getCapturableEntity(UUID uuid) {
        return getEntity(uuid)
                .flatMap(entity -> entity instanceof CapturableEntity ?
                        Mono.just((CapturableEntity) entity) :
                        Mono.error(EntityNotCapturableException.create(uuid))
                );
    }

    default Mono<InventoryView> createInventoryView(Inventory inventory) {
        return createInventoryView(inventory, null);
    }

    default Mono<InventoryMenuView> createInventoryMenuView(Inventory inventory) {
        return createInventoryMenuView(inventory, null);
    }

    default Mono<InventoryView> createInventoryView(Inventory inventory, Material airMaterial) {
        return createInventoryView(inventory, airMaterial, true);
    }

    default Mono<InventoryMenuView> createInventoryMenuView(Inventory inventory, Material airMaterial) {
        return createInventoryMenuView(inventory, airMaterial, true);
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
