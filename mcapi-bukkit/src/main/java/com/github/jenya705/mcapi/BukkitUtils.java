package com.github.jenya705.mcapi;

import lombok.Setter;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Jenya705
 */
@UtilityClass
public class BukkitUtils {

    @Setter
    private static JavaPlugin plugin;

    public void notAsyncTask(Runnable runnable) {
        if (Bukkit.isPrimaryThread()) {
            runnable.run();
        }
        else {
            Bukkit.getServer().getScheduler().runTask(plugin, runnable);
        }
    }
}
