package com.github.jenya705.mcapi;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/**
 * @author Jenya705
 */
@UtilityClass
public class BukkitUtils {

    private JavaPlugin plugin;

    public void setPlugin(JavaPlugin plugin) {
        BukkitUtils.plugin = plugin;
    }

    public void notAsyncTask(Runnable runnable) {
        if (Bukkit.isPrimaryThread()) {
            runnable.run();
        }
        else {
            Bukkit.getServer().getScheduler().runTask(plugin, runnable);
        }
    }

    public <T> T notAsyncSupplier(Supplier<T> supplier) {
        if (Bukkit.isPrimaryThread()) {
            return supplier.get();
        }
        AtomicReference<T> atomicValue = new AtomicReference<>();
        Bukkit.getServer().getScheduler().runTask(plugin, () -> {
            atomicValue.set(supplier.get());
            synchronized (atomicValue) {
                atomicValue.notifyAll();
            }
        });
        synchronized (atomicValue) {
            try {
                atomicValue.wait(1000 * 10); // 10 sec waiting
            } catch (InterruptedException e) {
                // ignored
            }
        }
        T value = atomicValue.get();
        if (value == null) {
            throw new IllegalArgumentException("Or supplier returns null, or bukkit server is lagging");
        }
        return value;
    }

}
