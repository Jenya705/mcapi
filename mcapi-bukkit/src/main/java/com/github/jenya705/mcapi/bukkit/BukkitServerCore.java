package com.github.jenya705.mcapi.bukkit;

import com.github.jenya705.mcapi.Material;
import com.github.jenya705.mcapi.bukkit.inventory.BukkitInventoryViewWrapper;
import com.github.jenya705.mcapi.bukkit.menu.BukkitInventoryMenuImpl;
import com.github.jenya705.mcapi.bukkit.menu.BukkitMenuManager;
import com.github.jenya705.mcapi.bukkit.permission.PermissionManagerHook;
import com.github.jenya705.mcapi.bukkit.player.BukkitOfflinePlayerStorage;
import com.github.jenya705.mcapi.bukkit.wrapper.BukkitWrapper;
import com.github.jenya705.mcapi.entity.Entity;
import com.github.jenya705.mcapi.inventory.Inventory;
import com.github.jenya705.mcapi.menu.InventoryMenuView;
import com.github.jenya705.mcapi.player.OfflinePlayer;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.server.ServerCore;
import com.github.jenya705.mcapi.server.command.CommandExecutor;
import com.github.jenya705.mcapi.server.util.ListUtils;
import com.github.jenya705.mcapi.world.World;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.experimental.Delegate;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.PluginCommand;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
@Singleton
public class BukkitServerCore implements ServerCore {

    @Delegate
    private final BukkitFileCore fileCore;

    private final BukkitApplication plugin;
    private final BukkitMenuManager menuManager;
    private final PermissionManagerHook permissionManagerHook;
    private final BukkitOfflinePlayerStorage offlinePlayerStorage;

    @Inject
    public BukkitServerCore(BukkitApplication plugin, BukkitMenuManager menuManager, BukkitFileCore fileCore,
                            PermissionManagerHook permissionManagerHook, BukkitOfflinePlayerStorage offlinePlayerStorage) {
        this.plugin = plugin;
        this.menuManager = menuManager;
        this.fileCore = fileCore;
        this.permissionManagerHook = permissionManagerHook;
        this.offlinePlayerStorage = offlinePlayerStorage;
        plugin.getDataFolder().mkdir();
        if (permissionManagerHook == null) {
            plugin.getLogger().severe(
                    "Permission manager can not be found. " +
                            "Maybe you are not using LuckPerms or did not download Vault, or did not download permission plugin for Vault. " +
                            "Because of that, bot commands can not be given to player on the linking state"
            );
        }
    }

    @Override
    public void addCommand(String name, CommandExecutor executor, String permissionName) {
        PluginCommand command = plugin.getCommand(name);
        command.setExecutor(new BukkitCommandWrapper(plugin, executor, permissionName));
        Bukkit.getOnlinePlayers().forEach(org.bukkit.entity.Player::updateCommands);
    }

    @Override
    public void permission(String name, boolean toggled) {
        List<String> subPermissions = generatePermissionList(name);
        PermissionDefault bukkitToggled = toggled ? PermissionDefault.TRUE : PermissionDefault.OP;
        PluginManager pluginManager = Bukkit.getPluginManager();
        for (int i = 0; i < subPermissions.size(); ++i) {
            Permission permission = pluginManager.getPermission(subPermissions.get(i));
            if (permission == null) {
                permission = new Permission(subPermissions.get(i), bukkitToggled);
                pluginManager.addPermission(permission);
            }
            for (int j = 0; j < i; ++j) {
                permission.addParent(subPermissions.get(j) + ".*", true);
            }
        }
    }

    @Override
    public void givePermission(Player player, boolean toggled, String... permissions) {
        if (permissionManagerHook == null) return;
        permissionManagerHook.givePermission(player, toggled, permissions);
    }

    private List<String> generatePermissionList(String name) {
        List<String> array = new ArrayList<>();
        int current = 0;
        while ((current = name.indexOf('.', current + 1)) != -1) {
            array.add(name.substring(0, current));
        }
        array.add(name);
        return array;
    }

    @Override
    public Collection<Player> getPlayers() {
        return Bukkit
                .getOnlinePlayers()
                .stream()
                .map(BukkitWrapper::player)
                .collect(Collectors.toList());
    }

    @Override
    public Player getPlayer(String name) {
        return BukkitWrapper.player(Bukkit.getPlayer(name));
    }

    @Override
    public Player getPlayer(UUID uuid) {
        return BukkitWrapper.player(Bukkit.getPlayer(uuid));
    }

    @Override
    public OfflinePlayer getOfflinePlayer(String name) {
        return BukkitWrapper.offlinePlayer(offlinePlayerStorage.getPlayer(name));
    }

    @Override
    public OfflinePlayer getOfflinePlayer(UUID uuid) {
        return BukkitWrapper.offlinePlayer(Bukkit.getOfflinePlayer(uuid));
    }

    @Override
    public Entity getEntity(UUID uuid) {
        return BukkitWrapper.entity(
                BukkitUtils.notAsyncSupplier(() -> Bukkit.getEntity(uuid))
        );
    }

    @Override
    public Collection<? extends World> getWorlds() {
        return Bukkit
                .getWorlds()
                .stream()
                .map(BukkitWrapper::world)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends Entity> getEntities(Predicate<Entity> predicate) {
        return ListUtils.join(
                Bukkit
                        .getWorlds()
                        .stream()
                        .map(org.bukkit.World::getEntities)
                        .map(entities -> entities
                                .stream()
                                .map(BukkitWrapper::entity)
                                .filter(predicate)
                                .collect(Collectors.toList())
                        )
                        .collect(Collectors.toList())
        );
    }

    @Override
    public BukkitInventoryViewWrapper createInventoryView(Inventory inventory, Material airMaterial, boolean unique) {
        return BukkitWrapper.inventoryView(inventory, airMaterial, unique);
    }

    @Override
    public InventoryMenuView createInventoryMenuView(Inventory inventory, Material airMaterial, boolean unique) {
        return new BukkitInventoryMenuImpl(
                menuManager,
                createInventoryView(inventory, airMaterial, unique),
                inventory
        );
    }

    @Override
    public Collection<? extends Entity> getEntities() {
        return getEntities(it -> true);
    }

    @Override
    public World getWorld(com.github.jenya705.mcapi.NamespacedKey id) {
        return BukkitWrapper.world(Bukkit.getWorld(BukkitWrapper.namespacedKey(id)));
    }

}
