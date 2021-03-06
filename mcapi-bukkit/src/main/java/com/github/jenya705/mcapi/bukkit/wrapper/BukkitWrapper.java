package com.github.jenya705.mcapi.bukkit.wrapper;

import com.github.jenya705.mcapi.*;
import com.github.jenya705.mcapi.block.Block;
import com.github.jenya705.mcapi.block.BlockFace;
import com.github.jenya705.mcapi.block.Half;
import com.github.jenya705.mcapi.block.Shape;
import com.github.jenya705.mcapi.block.data.Door;
import com.github.jenya705.mcapi.block.data.RedstoneWire;
import com.github.jenya705.mcapi.block.data.Slab;
import com.github.jenya705.mcapi.bukkit.BukkitCommandSenderWrapper;
import com.github.jenya705.mcapi.bukkit.BukkitLocationWrapper;
import com.github.jenya705.mcapi.bukkit.block.BukkitBlockWrapper;
import com.github.jenya705.mcapi.bukkit.enchantment.BukkitEnchantmentWrapper;
import com.github.jenya705.mcapi.bukkit.enchantment.BukkitItemEnchantmentWrapper;
import com.github.jenya705.mcapi.bukkit.entity.BukkitEntityWrapper;
import com.github.jenya705.mcapi.bukkit.entity.BukkitLivingEntityWrapper;
import com.github.jenya705.mcapi.bukkit.inventory.*;
import com.github.jenya705.mcapi.bukkit.player.BukkitOfflinePlayerWrapper;
import com.github.jenya705.mcapi.bukkit.player.BukkitPlayerWrapper;
import com.github.jenya705.mcapi.bukkit.potion.BukkitPotionEffectWrapper;
import com.github.jenya705.mcapi.bukkit.world.BukkitWorldWrapper;
import com.github.jenya705.mcapi.enchantment.Enchantment;
import com.github.jenya705.mcapi.enchantment.ItemEnchantment;
import com.github.jenya705.mcapi.entity.Entity;
import com.github.jenya705.mcapi.entity.LivingEntity;
import com.github.jenya705.mcapi.inventory.Inventory;
import com.github.jenya705.mcapi.inventory.InventoryView;
import com.github.jenya705.mcapi.inventory.ItemStack;
import com.github.jenya705.mcapi.inventory.PlayerInventory;
import com.github.jenya705.mcapi.player.GameMode;
import com.github.jenya705.mcapi.player.OfflinePlayer;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.potion.PotionEffect;
import com.github.jenya705.mcapi.potion.PotionEffectType;
import com.github.jenya705.mcapi.world.World;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
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
        return Vector3.of(
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
        if (entity instanceof org.bukkit.entity.LivingEntity livingEntity) {
            return livingEntity(livingEntity);
        }
        return BukkitEntityWrapper.of(entity);
    }

    public LivingEntity livingEntity(org.bukkit.entity.LivingEntity livingEntity) {
        if (livingEntity instanceof org.bukkit.entity.Player player) {
            return player(player);
        }
        return BukkitLivingEntityWrapper.of(livingEntity);
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

    public org.bukkit.block.Block block(Block block) {
        Location location = block.getLocation();
        NamespacedKey key = namespacedKey(location.getWorld().getId());
        if (key == null) return null;
        org.bukkit.World world = Bukkit.getWorld(key);
        if (world == null) return null;
        return world.getBlockAt(
                (int) location.getX(),
                (int) location.getY(),
                (int) location.getZ()
        );
    }

    public Block block(org.bukkit.block.Block block) {
        return BukkitBlockWrapper.of(block);
    }

    public ItemStack itemStack(org.bukkit.inventory.ItemStack itemStack) {
        return BukkitItemStackWrapper.of(itemStack);
    }

    public BukkitInventoryViewWrapper inventoryView(Inventory inventory, boolean unique) {
        return inventoryView(inventory, null, unique);
    }

    public BukkitInventoryViewWrapper inventoryView(Inventory inventory, Material airMaterial, boolean unique) {
        return unique ?
                BukkitUniqueInventoryViewWrapper.of(inventory, airMaterial) :
                BukkitSharedInventoryViewWrapper.of(inventory, airMaterial);
    }

    public org.bukkit.inventory.ItemStack itemStack(ItemStack itemStack) {
        if (itemStack instanceof BukkitItemStackWrapper bukkitItemStackWrapper) {
            return bukkitItemStackWrapper.getItemStack();
        }
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
        return material == null || !material.getKey().getDomain().equals("minecraft") ?
                org.bukkit.Material.AIR :
                org.bukkit.Material.valueOf(
                        material.getKey().getKey().toUpperCase(Locale.ROOT)
                );
    }

    public Inventory inventory(org.bukkit.inventory.Inventory inventory) {
        if (inventory == null) return null;
        if (inventory instanceof org.bukkit.inventory.PlayerInventory playerInventory) {
            return playerInventory(playerInventory);
        }
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

    public org.bukkit.inventory.Inventory inventory(Inventory inventory) {
        if (inventory instanceof BukkitInventoryWrapper inventoryWrapper) {
            return inventoryWrapper.getBukkitInventory();
        }
        return copyInventory(inventory);
    }

    public org.bukkit.inventory.Inventory copyInventory(Inventory inventory) {
        org.bukkit.inventory.Inventory bukkitInventory;
        bukkitInventory = Bukkit.createInventory(null, inventory.getSize());
        for (ItemStack itemStack : inventory.getAllItems()) {
            bukkitInventory.addItem(BukkitWrapper.itemStack(itemStack));
        }
        return bukkitInventory;
    }

    public org.bukkit.inventory.Inventory copyInventory(InventoryView inventoryView) {
        org.bukkit.inventory.Inventory bukkitInventory =
                Bukkit.createInventory(null, inventoryView.getInventory().getSize());
        int index = 0;
        for (ItemStack itemStack : inventoryView.getInventory().getAllItems()) {
            org.bukkit.inventory.ItemStack bukkitItemStack;
            if (itemStack == null || VanillaMaterial.AIR.equals(itemStack.getMaterial())) {
                if (!inventoryView.getAirMaterial().isItem()) {
                    continue;
                }
                bukkitItemStack = new org.bukkit.inventory.ItemStack(
                        material(inventoryView.getAirMaterial())
                );
            }
            else {
                bukkitItemStack = BukkitWrapper.itemStack(itemStack);
            }
            bukkitInventory.setItem(index++, bukkitItemStack);
        }
        return bukkitInventory;
    }

    public PotionEffectType potionEffectType(org.bukkit.potion.PotionEffectType type) {
        return BukkitPotionWrapper.potionEffectType(type);
    }

    public org.bukkit.potion.PotionEffectType potionEffectType(PotionEffectType type) {
        return BukkitPotionWrapper.potionEffectType(type);
    }

    public PotionEffect potionEffect(org.bukkit.potion.PotionEffect potionEffect) {
        return BukkitPotionEffectWrapper.of(potionEffect);
    }

    public org.bukkit.potion.PotionEffect potionEffect(PotionEffect potionEffect) {
        if (potionEffect instanceof BukkitPotionEffectWrapper potionEffectWrapper) {
            return potionEffectWrapper.getBukkitPotionEffect();
        }
        return new org.bukkit.potion.PotionEffect(
                potionEffectType(potionEffect.getType()),
                (int) (potionEffect.getDuration() / 50),
                potionEffect.getAmplifier(),
                potionEffect.isAmbient(),
                !potionEffect.isHidden(),
                !potionEffect.isHidden()
        );
    }

    public com.github.jenya705.mcapi.NamespacedKey namespacedKey(NamespacedKey namespacedKey) {
        return com.github.jenya705.mcapi.NamespacedKey.from(namespacedKey.toString());
    }

    public NamespacedKey namespacedKey(com.github.jenya705.mcapi.NamespacedKey namespacedKey) {
        return NamespacedKey.fromString(namespacedKey.toString());
    }

}
