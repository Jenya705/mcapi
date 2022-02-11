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
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Jenya705
 */
@Singleton
public class BukkitInventoryMoveEventHandler implements Listener {

    private final EventLoop eventLoop;
    private final BukkitFullWrapper fullWrapper;

    private final Map<UUID, Boolean> itemTaken = new HashMap<>();

    @Inject
    public BukkitInventoryMoveEventHandler(EventLoop eventLoop, BukkitFullWrapper fullWrapper, BukkitApplication plugin) {
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
    public void clickInventory(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        if (consumeTaking(event, player) ||
                consumePlacing(event, player) ||
                consumeDropping(event, player) ||
                consumeMovingToOtherInventory(event, player)) return;
    }

    @EventHandler
    public void closeInventory(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player player) {
            clearItemTaken(player);
        }
    }

    private void inventoryMoveEvent(Inventory destination, Inventory source, ItemStack itemStack, InventoryHolder holder, Player player) {
        if (Objects.equals(source, destination)) return; // no moving from inventory to inventory
        eventLoop.invoke(new EntityInventoryMoveEvent(
                BukkitWrapper.inventory(destination),
                BukkitWrapper.inventory(source),
                BukkitWrapper.itemStack(itemStack),
                fullWrapper.holderToLocal(holder),
                BukkitWrapper.player(player)
        ));
    }

    private Inventory getItemTakenInventory(InventoryClickEvent event, Player player) {
        if (itemTaken.containsKey(player.getUniqueId())) {
            return itemTaken.get(player.getUniqueId()) ?
                    event.getInventory() : player.getInventory();
        }
        return null;
    }

    private void updateItemTaken(InventoryClickEvent event, Player player) {
        itemTaken.put(
                player.getUniqueId(),
                Objects.equals(event.getInventory(), event.getClickedInventory())
        );
    }

    private void clearItemTaken(Player player) {
        itemTaken.remove(player.getUniqueId());
    }

    private boolean consumeTaking(InventoryClickEvent event, Player player) {
        InventoryAction action = event.getAction();
        if (action == InventoryAction.PICKUP_ALL ||
                action == InventoryAction.PICKUP_ONE ||
                action == InventoryAction.PICKUP_SOME ||
                action == InventoryAction.PICKUP_HALF) {
            updateItemTaken(event, player);
            return true;
        }
        return false;
    }

    private boolean consumePlacing(InventoryClickEvent event, Player player) {
        InventoryAction action = event.getAction();
        ItemStack cursor = event.getCursor();
        if (action == InventoryAction.SWAP_WITH_CURSOR) {
            consumePlacingWithoutChecking(event, player, cursor.getAmount());
            updateItemTaken(event, player);
            return true;
        }
        ItemStack item = event.getCurrentItem();
        boolean clearItem = false;
        int amount = switch (action) {
            case PLACE_ONE -> {
                clearItem = cursor.getAmount() == 1;
                yield 1;
            }
            case PLACE_ALL -> {
                clearItem = true;
                yield cursor.getAmount();
            }
            case PLACE_SOME -> item.getMaxStackSize() - item.getAmount();
            default -> -1;
        };
        if (amount == -1) return false;
        consumePlacingWithoutChecking(event, player, amount);
        if (clearItem) clearItemTaken(player);
        return true;
    }

    private void consumePlacingWithoutChecking(InventoryClickEvent event, Player player, int amount) {
        ItemStack cursor = event.getCursor();
        if (cursor == null) return;
        Inventory holderInventory = event.getClickedInventory();
        ItemStack newCursor = new ItemStack(cursor);
        newCursor.setAmount(amount);
        inventoryMoveEvent(
                event.getClickedInventory(),
                getItemTakenInventory(event, player),
                newCursor,
                holderInventory.getHolder(),
                player
        );
    }

    private boolean consumeDropping(InventoryClickEvent event, Player player) {
        InventoryAction action = event.getAction();
        ItemStack dropped;
        Inventory source;
        int amount;
        if (action == InventoryAction.DROP_ALL_SLOT) {
            dropped = event.getCurrentItem();
            amount = dropped.getAmount();
            source = event.getClickedInventory();
        }
        else if (action == InventoryAction.DROP_ALL_CURSOR) {
            dropped = event.getCursor();
            amount = dropped.getAmount();
            source = getItemTakenInventory(event, player);
        }
        else if (action == InventoryAction.DROP_ONE_SLOT) {
            dropped = event.getCurrentItem();
            amount = 1;
            source = event.getClickedInventory();
        }
        else if (action == InventoryAction.DROP_ONE_CURSOR) {
            dropped = event.getCursor();
            amount = 1;
            source = getItemTakenInventory(event, player);
        }
        else {
            return false;
        }
        ItemStack endItem = new ItemStack(dropped);
        endItem.setAmount(amount);
        inventoryMoveEvent(
                null,
                source,
                endItem,
                source == null ? null : source.getHolder(),
                player
        );
        clearItemTaken(player);
        return true;
    }

    private boolean consumeMovingToOtherInventory(InventoryClickEvent event, Player player) {
        if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
            Inventory clickedInventory = event.getClickedInventory();
            Inventory destination = getNotClickedInventory(event);
            ItemStack item = event.getCurrentItem();
            int itemSpace = BukkitInventoryUtils.getItemSpace(destination, item);
            if (itemSpace <= 0) {
                return true;
            }
            int amount = Math.min(item.getAmount(), itemSpace);
            ItemStack resultItem = new ItemStack(item);
            resultItem.setAmount(amount);
            inventoryMoveEvent(
                    destination,
                    clickedInventory,
                    resultItem,
                    destination.getHolder(),
                    player
            );
            return true;
        }
        return false;
    }

    private static Inventory getNotClickedInventory(InventoryClickEvent event) {
        return Objects.equals(event.getInventory(), event.getClickedInventory()) ?
                event.getWhoClicked().getInventory() : event.getInventory();
    }

}
