package com.github.jenya705.mcapi.bukkit.menu;

import com.github.jenya705.mcapi.bukkit.BukkitApplication;
import com.github.jenya705.mcapi.bukkit.BukkitWrapper;
import com.github.jenya705.mcapi.menu.InventoryMenuView;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.server.application.OnStartup;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Jenya705
 */
@Singleton
public class BukkitMenuManagerImpl implements Listener, BukkitMenuManager {

    private final BukkitApplication plugin;

    private final Map<UUID, InventoryMenuView> menus = new HashMap<>();

    @Inject
    public BukkitMenuManagerImpl(BukkitApplication plugin) {
        this.plugin = plugin;
    }

    @Override
    public void register(Player player, InventoryMenuView menu) {
        menus.put(player.getUuid(), menu);
    }

    @Override
    public void unregister(Player player) {
        InventoryMenuView inventoryMenu = menus.remove(player.getUuid());
        if (inventoryMenu != null) inventoryMenu.close(player);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!event.getInventory().equals(event.getClickedInventory())) return;
        if (!(event.getWhoClicked() instanceof org.bukkit.entity.Player player)) return;
        InventoryMenuView inventoryMenuView = menus.get(player.getUniqueId());
        if (inventoryMenuView == null) return;
        inventoryMenuView.clicked(BukkitWrapper.player(player), event.getSlot());
        event.setCancelled(true);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof org.bukkit.entity.Player player) {
            unregister(BukkitWrapper.player(player));
        }
    }

    @OnStartup
    public void registerSelf() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
}
