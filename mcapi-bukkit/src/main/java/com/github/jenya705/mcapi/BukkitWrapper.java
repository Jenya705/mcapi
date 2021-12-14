package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.block.*;
import com.github.jenya705.mcapi.block.data.Door;
import com.github.jenya705.mcapi.block.data.RedstoneWire;
import com.github.jenya705.mcapi.block.data.Slab;
import com.github.jenya705.mcapi.enchantment.BukkitEnchantmentWrapper;
import com.github.jenya705.mcapi.enchantment.BukkitItemEnchantmentWrapper;
import com.github.jenya705.mcapi.enchantment.Enchantment;
import com.github.jenya705.mcapi.enchantment.ItemEnchantment;
import com.github.jenya705.mcapi.entity.BukkitEntityWrapper;
import com.github.jenya705.mcapi.entity.Entity;
import com.github.jenya705.mcapi.inventory.*;
import com.github.jenya705.mcapi.player.BukkitPlayerWrapper;
import com.github.jenya705.mcapi.player.GameMode;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.world.World;
import lombok.experimental.UtilityClass;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.type.Stairs;

import java.util.Locale;

/**
 * @author Jenya705
 */
@UtilityClass
public class BukkitWrapper {

    public CommandSender sender(org.bukkit.command.CommandSender sender) {
        if (sender instanceof org.bukkit.entity.Player) {
            return BukkitPlayerWrapper.of((org.bukkit.entity.Player) sender);
        }
        return BukkitCommandSenderWrapper.of(sender);
    }

    public OfflinePlayer offlinePlayer(org.bukkit.OfflinePlayer player) {
        if (player instanceof org.bukkit.entity.Player && player.isOnline()) {
            return BukkitPlayerWrapper.of((org.bukkit.entity.Player) player);
        }
        return BukkitOfflinePlayerWrapper.of(player);
    }

    public Vector3 vector(org.bukkit.util.Vector vector) {
        return new Vector3(
                vector.getX(),
                vector.getY(),
                vector.getZ()
        );
    }

    public BoundingBox boundingBox(org.bukkit.util.BoundingBox boundingBox) {
        return new BoundingBox(
                vector(boundingBox.getMin()),
                vector(boundingBox.getMax())
        );
    }

    public Entity entity(org.bukkit.entity.Entity entity) {
        if (entity instanceof org.bukkit.entity.Player) {
            return BukkitPlayerWrapper.of((org.bukkit.entity.Player) entity);
        }
        return BukkitEntityWrapper.of(entity);
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

    public Block block(org.bukkit.block.Block block) {
        return BukkitBlockWrapper.of(block);
    }

    public ItemStack itemStack(org.bukkit.inventory.ItemStack itemStack) {
        return BukkitItemStackWrapper.of(itemStack);
    }

    public org.bukkit.inventory.ItemStack itemStack(ItemStack itemStack) {
        org.bukkit.inventory.ItemStack bukkitItemStack = new org.bukkit.inventory.ItemStack(
                material(itemStack.getMaterial())
        );
        bukkitItemStack.setAmount(
                Math.max(1, Math.min(64, itemStack.getAmount()))
        );
        if (itemStack.customName() != null) {
            org.bukkit.inventory.meta.ItemMeta bukkitItemMeta = bukkitItemStack.getItemMeta();
            bukkitItemMeta.displayName(itemStack.customName());
            bukkitItemStack.setItemMeta(bukkitItemMeta);
        }
        return bukkitItemStack;
    }

    public Material material(org.bukkit.Material material) {
        return VanillaMaterial.getMaterial(material.getKey().getKey());
    }

    public org.bukkit.Material material(Material material) {
        return org.bukkit.Material.valueOf(
                material.getKey().substring("minecraft:".length()).toUpperCase(Locale.ROOT)
        );
    }

    public Inventory inventory(org.bukkit.inventory.Inventory inventory) {
        return BukkitInventoryWrapper.of(inventory);
    }

    public PlayerInventory playerInventory(org.bukkit.inventory.PlayerInventory playerInventory) {
        return BukkitPlayerInventoryWrapper.of(playerInventory);
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

    public BlockFace blockFace(org.bukkit.block.BlockFace blockFace) {
        return BlockFace.valueOf(blockFace.name());
    }

    public Half half(Bisected.Half half) {
        return Half.valueOf(half.name());
    }

    public Slab.SlabType slabType(org.bukkit.block.data.type.Slab.Type type) {
        return Slab.SlabType.valueOf(type.name());
    }

    public Shape shape(Stairs.Shape shape) {
        return Shape.valueOf(shape.name());
    }

    public RedstoneWire.Connection connection(org.bukkit.block.data.type.RedstoneWire.Connection connection) {
        return RedstoneWire.Connection.valueOf(connection.name());
    }

    public Door.Hinge hinge(org.bukkit.block.data.type.Door.Hinge hinge) {
        return Door.Hinge.valueOf(hinge.name());
    }

    public org.bukkit.block.data.type.RedstoneWire.Connection connection(RedstoneWire.Connection connection) {
        return org.bukkit.block.data.type.RedstoneWire.Connection.valueOf(connection.name());
    }

    public org.bukkit.block.BlockFace blockFace(BlockFace blockFace) {
        return org.bukkit.block.BlockFace.valueOf(blockFace.name());
    }

    public Bisected.Half half(Half half) {
        return Bisected.Half.valueOf(half.name());
    }

    public Stairs.Shape shape(Shape shape) {
        return Stairs.Shape.valueOf(shape.name());
    }

    public org.bukkit.block.data.type.Slab.Type slabType(Slab.SlabType type) {
        return org.bukkit.block.data.type.Slab.Type.valueOf(type.name());
    }

    public org.bukkit.block.data.type.Door.Hinge hinge(Door.Hinge hinge) {
        return org.bukkit.block.data.type.Door.Hinge.valueOf(hinge.name());
    }

}
