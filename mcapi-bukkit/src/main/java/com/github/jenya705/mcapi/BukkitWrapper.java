package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.block.BlockFace;
import com.github.jenya705.mcapi.block.Half;
import com.github.jenya705.mcapi.block.Shape;
import com.github.jenya705.mcapi.enchantment.BukkitEnchantmentWrapper;
import com.github.jenya705.mcapi.enchantment.BukkitItemEnchantmentWrapper;
import com.github.jenya705.mcapi.enchantment.Enchantment;
import com.github.jenya705.mcapi.enchantment.ItemEnchantment;
import com.github.jenya705.mcapi.inventory.*;
import com.github.jenya705.mcapi.player.BukkitPlayerWrapper;
import com.github.jenya705.mcapi.player.GameMode;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.world.World;
import lombok.experimental.UtilityClass;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.type.Stairs;

/**
 * @author Jenya705
 */
@UtilityClass
public class BukkitWrapper {

    public CommandSender sender(org.bukkit.command.CommandSender sender) {
        if (sender instanceof Player) {
            return BukkitPlayerWrapper.of((org.bukkit.entity.Player) sender);
        }
        return BukkitCommandSenderWrapper.of(sender);
    }

    public OfflinePlayer offlinePlayer(org.bukkit.OfflinePlayer player) {
        if (player instanceof Player && player.isOnline()) {
            return BukkitPlayerWrapper.of((org.bukkit.entity.Player) player);
        }
        return BukkitOfflinePlayerWrapper.of(player);
    }

    public Player player(org.bukkit.entity.Player player) {
        return BukkitPlayerWrapper.of(player);
    }

    public Location location(org.bukkit.Location location) {
        return BukkitLocationWrapper.of(location);
    }

    public World world(org.bukkit.World world) {
        return BukkitWorldWrapper.of(world);
    }

    public ItemStack itemStack(org.bukkit.inventory.ItemStack itemStack) {
        return BukkitItemStackWrapper.of(itemStack);
    }

    public Inventory inventory(org.bukkit.inventory.Inventory inventory) {
        return BukkitInventoryWrapper.of(inventory);
    }

    public PlayerInventory playerInventory(org.bukkit.inventory.PlayerInventory playerInventory) {
        return BukkitPlayerInventoryWrapper.of(playerInventory);
    }

    public BlockFace blockFace(org.bukkit.block.BlockFace blockFace) {
        return switch (blockFace) {
            case EAST -> BlockFace.EAST;
            case WEST -> BlockFace.WEST;
            case NORTH -> BlockFace.NORTH;
            case SOUTH -> BlockFace.SOUTH;
            default -> null;
        };
    }

    public GameMode gamemode(org.bukkit.GameMode gameMode) {
        return GameMode.valueOf(gameMode.name());
    }

    public Enchantment enchantment(org.bukkit.enchantments.Enchantment enchantment) {
        return BukkitEnchantmentWrapper.of(enchantment);
    }

    public ItemEnchantment itemEnchantment(org.bukkit.enchantments.Enchantment enchantment, int level) {
        return BukkitItemEnchantmentWrapper.of(enchantment, level);
    }

    public Half half(Bisected.Half half) {
        return Half.valueOf(half.name());
    }

    public Shape shape(Stairs.Shape shape) {
        return Shape.valueOf(shape.name());
    }

}
