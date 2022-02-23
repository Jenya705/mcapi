package com.github.jenya705.mcapi.bukkit;

import com.github.jenya705.mcapi.server.util.ValueContainer;
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

    private final AtomicReference<JavaPlugin> pluginReference = new AtomicReference<>();

    public JavaPlugin getPlugin() {
        return pluginReference.get();
    }

    public void setPlugin(JavaPlugin plugin) {
        pluginReference.set(plugin);
    }

    public void notAsyncTask(Runnable runnable) {
        if (Bukkit.isPrimaryThread()) {
            runnable.run();
        } else {
            Bukkit.getServer().getScheduler().runTask(getPlugin(), runnable);
        }
    }

    public void blockingNotAsyncTask(Runnable runnable) {
        if (Bukkit.isPrimaryThread()) {
            runnable.run();
        }
        else {
            Bukkit.getServer().getScheduler().runTask(getPlugin(), () -> {
                runnable.run();
                synchronized (runnable) {
                    runnable.notifyAll();
                }
            });
            synchronized (runnable) {
                try {
                    runnable.wait(10 * 1000); // 10 secs
                } catch (InterruptedException e) {
                    // ignored
                }
            }
        }
    }

    public <T> T notAsyncSupplier(Supplier<T> supplier) {
        if (Bukkit.isPrimaryThread()) {
            return supplier.get();
        }
        AtomicReference<ValueContainer<T>> atomicValue = new AtomicReference<>();
        blockingNotAsyncTask(() -> atomicValue.set(new ValueContainer<>(supplier.get())));
        ValueContainer<T> value = atomicValue.get();
        if (value == null) {
            throw new RuntimeException("Bukkit server is lagging");
        }
        return value.getValue();
    }

}
