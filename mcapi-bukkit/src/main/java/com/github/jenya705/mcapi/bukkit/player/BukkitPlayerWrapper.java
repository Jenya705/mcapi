package com.github.jenya705.mcapi.bukkit.player;

import com.github.jenya705.mcapi.bukkit.BukkitCommandSenderWrapper;
import com.github.jenya705.mcapi.bukkit.BukkitUtils;
import com.github.jenya705.mcapi.bukkit.BukkitWrapper;
import com.github.jenya705.mcapi.bukkit.entity.BukkitLivingEntityWrapper;
import com.github.jenya705.mcapi.bukkit.inventory.BukkitInventoryViewWrapper;
import com.github.jenya705.mcapi.entity.LivingEntity;
import com.github.jenya705.mcapi.inventory.Inventory;
import com.github.jenya705.mcapi.inventory.InventoryView;
import com.github.jenya705.mcapi.inventory.PlayerInventory;
import com.github.jenya705.mcapi.player.GameMode;
import com.github.jenya705.mcapi.player.PlayerAbilities;
import lombok.experimental.Delegate;
import net.kyori.adventure.text.Component;

/**
 * @author Jenya705
 */
public class BukkitPlayerWrapper extends BukkitCommandSenderWrapper implements BukkitPlayer {

    private final org.bukkit.entity.Player player;

    @Delegate
    private final LivingEntity entityDelegate;

    private PlayerAbilities abilities;

    private final PlayerInventory inventory;
    private final Inventory enderChest;

    public BukkitPlayerWrapper(org.bukkit.entity.Player player) {
        super(player);
        this.player = player;
        entityDelegate = new BukkitLivingEntityWrapper(player);
        enderChest = BukkitWrapper.inventory(player.getEnderChest());
        inventory = BukkitWrapper.playerInventory(player.getInventory());
    }

    public static BukkitPlayerWrapper of(org.bukkit.entity.Player player) {
        return player == null ? null : new BukkitPlayerWrapper(player);
    }

    @Override
    public org.bukkit.entity.Player getBukkit() {
        return player;
    }

    @Override
    public InventoryView openInventory(InventoryView inventory, boolean sayAboutSelf) {
        if (sayAboutSelf) {
            inventory.open(this);
            return inventory;
        }
        org.bukkit.inventory.Inventory bukkitInventory;
        if (inventory instanceof BukkitInventoryViewWrapper bukkitInventoryViewWrapper) {
            bukkitInventory = bukkitInventoryViewWrapper.getBukkitInventory();
        }
        else {
            bukkitInventory = BukkitWrapper.copyInventory(inventory);
        }
        BukkitUtils.notAsyncTask(() -> player.openInventory(bukkitInventory));
        return inventory;
    }

    @Override
    public InventoryView openInventory(Inventory inventory) {
        InventoryView inventoryView = BukkitWrapper.inventoryView(inventory, false);
        inventoryView.open(this);
        return inventoryView;
    }

    @Override
    public void closeInventory() {
        BukkitUtils.notAsyncTask(player::closeInventory);
    }

    @Override
    public void chat(String message) {
        player.chat(message);
    }

    @Override
    public void runCommand(String command) {
        player.performCommand(command);
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public void sendMessage(Component component) {
        player.sendMessage(component);
    }

    @Override
    public void kick(String reason) {
        BukkitUtils.notAsyncTask(() -> player.kick(Component.text(reason)));
    }

    @Override
    public void ban(String reason) {
        BukkitUtils.notAsyncTask(() -> player.banPlayer(reason));
    }

    @Override
    public boolean isOnline() {
        return player.isOnline();
    }

    @Override
    public PlayerInventory getInventory() {
        return inventory;
    }

    @Override
    public Inventory getEnderChest() {
        return enderChest;
    }

    @Override
    public GameMode getGameMode() {
        return BukkitWrapper.gamemode(player.getGameMode());
    }

    @Override
    public PlayerAbilities getAbilities() {
        if (abilities == null) {
            abilities = new BukkitPlayerAbilities(player);
        }
        return abilities;
    }

}
