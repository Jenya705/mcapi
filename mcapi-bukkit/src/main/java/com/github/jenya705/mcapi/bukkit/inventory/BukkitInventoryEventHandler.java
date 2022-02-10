package com.github.jenya705.mcapi.bukkit.inventory;

import com.github.jenya705.mcapi.bukkit.BukkitApplication;
import com.github.jenya705.mcapi.bukkit.wrapper.BukkitFullWrapper;
import com.github.jenya705.mcapi.bukkit.wrapper.BukkitWrapper;
import com.github.jenya705.mcapi.entity.event.EntityInventoryMoveEvent;
import com.github.jenya705.mcapi.server.event.EventLoop;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

/**
 * @author Jenya705
 */
@Singleton
public class BukkitInventoryEventHandler implements Listener {

    private final EventLoop eventLoop;
    private final BukkitFullWrapper fullWrapper;

    @Inject
    public BukkitInventoryEventHandler(EventLoop eventLoop, BukkitFullWrapper fullWrapper, BukkitApplication plugin) {
        this.eventLoop = eventLoop;
        this.fullWrapper = fullWrapper;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void moveItem(InventoryMoveItemEvent event) {
        if (event.getInitiator().getHolder() instanceof Player player) {
            inventoryMoveEvent(
                    event.getDestination(),
                    event.getInitiator(),
                    event.getItem(),
                    event.getDestination().getHolder(),
                    player
            );
        }
    }

    @EventHandler
    public void moveItem(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        if (event.isShiftClick()) {
            ItemStack currentItem = event.getCurrentItem();
            if (currentItem == null) return;
            Inventory holderInventory = Objects.equals(event.getInventory(), event.getClickedInventory()) ?
                    player.getInventory() : event.getInventory();
            inventoryMoveEvent(
                    holderInventory,
                    event.getClickedInventory(),
                    new ItemStack(currentItem),
                    holderInventory.getHolder(),
                    player
            );
            return;
        }
        if (event.getAction() == InventoryAction.PLACE_ALL ||
                event.getAction() == InventoryAction.PLACE_ONE ||
                event.getAction() == InventoryAction.PLACE_SOME) {
            ItemStack cursor = event.getCursor();
            if (cursor == null) return;
            Inventory holderInventory = Objects.equals(event.getInventory(), event.getClickedInventory()) ?
                    event.getInventory(): player.getInventory();
            inventoryMoveEvent(
                    event.getClickedInventory(),
                    null, // we don't know
                    new ItemStack(cursor),
                    holderInventory.getHolder(),
                    player
            );
        }
    }

    private void inventoryMoveEvent(Inventory destination, Inventory source, ItemStack itemStack, InventoryHolder holder, Player player) {
        eventLoop.invoke(new EntityInventoryMoveEvent(
                BukkitWrapper.inventory(destination),
                BukkitWrapper.inventory(source),
                BukkitWrapper.itemStack(itemStack),
                fullWrapper.holderToLocal(holder),
                BukkitWrapper.player(player)
        ));
    }


}
