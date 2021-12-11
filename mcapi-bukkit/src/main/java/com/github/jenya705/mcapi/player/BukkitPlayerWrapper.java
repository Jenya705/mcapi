package com.github.jenya705.mcapi.player;

import com.github.jenya705.mcapi.*;
import com.github.jenya705.mcapi.inventory.Inventory;
import com.github.jenya705.mcapi.inventory.PlayerInventory;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;

import java.util.UUID;

/**
 * @author Jenya705
 */
public class BukkitPlayerWrapper extends BukkitCommandSenderWrapper implements Player {

    private final org.bukkit.entity.Player player;

    private PlayerAbilities abilities;

    private final PlayerInventory inventory;
    private final Inventory enderChest;

    public BukkitPlayerWrapper(org.bukkit.entity.Player player) {
        super(player);
        this.player = player;
        enderChest = BukkitWrapper.inventory(player.getEnderChest());
        inventory = BukkitWrapper.playerInventory(player.getInventory());
    }

    public static BukkitPlayerWrapper of(org.bukkit.entity.Player player) {
        return player == null ? null : new BukkitPlayerWrapper(player);
    }

    @Override
    public void kill() {
        BukkitUtils.notAsyncTask(() -> player.setHealth(0d));
    }

    @Override
    public Vector3 getVelocity() {
        return BukkitWrapper.vector(player.getVelocity());
    }

    @Override
    public BoundingBox getBoundingBox() {
        return BukkitWrapper.boundingBox(player.getBoundingBox());
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public UUID getUuid() {
        return player.getUniqueId();
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
    public Location getLocation() {
        return BukkitWrapper.location(player.getLocation());
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
    public float getYaw() {
        return player.getLocation().getYaw();
    }

    @Override
    public float getPitch() {
        return player.getLocation().getPitch();
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

    @Override
    public float getAbsorptionAmount() {
        return (float) player.getAbsorptionAmount();
    }

    @Override
    public int getAirLeft() {
        return player.getRemainingAir();
    }

    @Override
    public float getFallDistance() {
        return player.getFallDistance();
    }

    @Override
    public boolean isFalling() {
        return player.getFallDistance() > 0f;
    }

    @Override
    public int getFireTicks() {
        return player.getFireTicks();
    }

    @Override
    public int getFoodLevel() {
        return player.getFoodLevel();
    }

    @Override
    public float getFoodExhaustionLevel() {
        return player.getExhaustion();
    }

    @Override
    public float getFoodSaturationLevel() {
        return player.getSaturation();
    }

    @Override
    public float getHealth() {
        return (float) player.getHealth();
    }

    @Override
    public Location getSpawn() {
        return BukkitWrapper.location(player.getBedSpawnLocation() == null ?
                Bukkit.getWorld(NamespacedKey.minecraft("overworld")).getSpawnLocation() :
                player.getBedSpawnLocation()
        );
    }

    @Override
    public int getXpLevel() {
        return player.getLevel();
    }

    @Override
    public int getXpPercentage() {
        return Math.round(player.getExp() * 100);
    }
}
