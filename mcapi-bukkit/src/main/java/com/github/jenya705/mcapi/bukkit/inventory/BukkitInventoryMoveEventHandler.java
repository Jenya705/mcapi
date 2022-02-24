package com.github.jenya705.mcapi.bukkit.inventory;

import com.github.jenya705.mcapi.bukkit.BukkitApplication;
import com.github.jenya705.mcapi.bukkit.menu.BukkitMenuManager;
import com.github.jenya705.mcapi.bukkit.wrapper.BukkitFullWrapper;
import com.github.jenya705.mcapi.bukkit.wrapper.BukkitWrapper;
import com.github.jenya705.mcapi.entity.event.EntityInventoryMoveEvent;
import com.github.jenya705.mcapi.server.event.EventLoop;
import com.github.jenya705.mcapi.server.util.Pair;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * @author Jenya705
 */
@Singleton
public class BukkitInventoryMoveEventHandler implements Listener {

    private static final InventoryHolder airHolder = null;

    private final EventLoop eventLoop;
    private final BukkitFullWrapper fullWrapper;

    private final Map<UUID, Pair<Boolean, Integer>> itemTaken = new HashMap<>();

    @Inject
    public BukkitInventoryMoveEventHandler(EventLoop eventLoop, BukkitFullWrapper fullWrapper, BukkitApplication plugin) {
        this.eventLoop = eventLoop;
        this.fullWrapper = fullWrapper;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void moveInventory(InventoryMoveItemEvent event) {
        ItemStack item = event.getItem();
        BukkitInventoryUtils
                .getPossibleItemIndexes(event.getDestination(), item)
                .forEach(pair -> {
                    int index = pair.getLeft();
                    int amount = pair.getRight();
                    ItemStack endItem = new ItemStack(item);
                    endItem.setAmount(amount);
                    inventoryMoveEvent(
                            index,
                            -1,
                            endItem,
                            event.getDestination().getHolder(),
                            event.getSource().getHolder(),
                            event.getInitiator().getHolder()
                    );
                });
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void clickInventory(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        if (consumeTaking(event, player) ||
                consumePlacing(event, player) ||
                consumeDropping(event, player) ||
                consumeMovingToOtherInventory(event, player)) {}
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void closeInventory(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player player) {
            clearItemTaken(player);
        }
    }

    private void inventoryMoveEvent(int destinationSlot,
                                    int fromSlot,
                                    ItemStack itemStack,
                                    InventoryHolder destinationHolder,
                                    InventoryHolder sourceHolder,
                                    InventoryHolder initiator) {
        com.github.jenya705.mcapi.inventory.InventoryHolder mcapiDestinationHolder =
                fullWrapper.holderToLocal(destinationHolder);
        com.github.jenya705.mcapi.inventory.InventoryHolder mcapiSourceHolder =
                Objects.equals(destinationHolder, sourceHolder) ?
                        mcapiDestinationHolder :
                        fullWrapper.holderToLocal(sourceHolder);
        com.github.jenya705.mcapi.inventory.InventoryHolder mcapiInitiatorHolder =
                Objects.equals(destinationHolder, initiator) ?
                        mcapiDestinationHolder :
                        Objects.equals(sourceHolder, initiator) ?
                                mcapiSourceHolder :
                                fullWrapper.holderToLocal(initiator);
        eventLoop.invoke(new EntityInventoryMoveEvent(
                destinationSlot,
                fromSlot,
                BukkitWrapper.itemStack(itemStack),
                mcapiDestinationHolder,
                mcapiSourceHolder,
                mcapiInitiatorHolder
        ));
    }

    private Inventory getItemTakenInventory(InventoryClickEvent event, Player player) {
        if (itemTaken.containsKey(player.getUniqueId())) {
            return itemTaken.get(player.getUniqueId()).getLeft() ?
                    event.getInventory() : player.getInventory();
        }
        return null;
    }

    private int getItemTakenSlot(Player player) {
        if (itemTaken.containsKey(player.getUniqueId())) {
            return itemTaken.get(player.getUniqueId()).getRight();
        }
        return -1;
    }

    private void updateItemTaken(InventoryClickEvent event, Player player, int slot) {
        itemTaken.put(
                player.getUniqueId(),
                new Pair<>(Objects.equals(event.getInventory(), event.getClickedInventory()), slot)
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
            updateItemTaken(event, player, event.getSlot());
            return true;
        }
        return false;
    }

    private boolean consumePlacing(InventoryClickEvent event, Player player) {
        InventoryAction action = event.getAction();
        ItemStack cursor = event.getCursor();
        if (cursor == null) return true;
        if (action == InventoryAction.SWAP_WITH_CURSOR) {
            consumePlacingWithoutChecking(event, player, cursor.getAmount());
            updateItemTaken(event, player, event.getSlot());
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
        Inventory holderInventory = event.getClickedInventory();
        if (cursor == null || cursor.getType().isAir() || holderInventory == null) return;
        ItemStack newCursor = new ItemStack(cursor);
        newCursor.setAmount(amount);
        Inventory itemTaken = getItemTakenInventory(event, player);
        inventoryMoveEvent(
                event.getSlot(),
                getItemTakenSlot(player),
                newCursor,
                holderInventory.getHolder(),
                itemTaken == null ? null : itemTaken.getHolder(),
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
                -1,
                getItemTakenSlot(player),
                endItem,
                airHolder,
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
            List<Pair<Integer, Integer>> possibleSlots = BukkitInventoryUtils.getPossibleItemIndexes(destination, item);
            possibleSlots.forEach(pair -> {
                int slot = pair.getLeft();
                int amount = pair.getRight();
                ItemStack endItem = new ItemStack(item);
                endItem.setAmount(amount);
                inventoryMoveEvent(
                        slot,
                        event.getSlot(),
                        endItem,
                        destination.getHolder(),
                        clickedInventory.getHolder(),
                        player
                );
            });
            return true;
        }
        return false;
    }

    private static Inventory getNotClickedInventory(InventoryClickEvent event) {
        return Objects.equals(event.getInventory(), event.getClickedInventory()) ?
                event.getWhoClicked().getInventory() : event.getInventory();
    }

}
