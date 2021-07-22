package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.common.CompoundClassLoader;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * An application starter for bukkit
 *
 * @see JavaServerApplication
 * @since 1.0
 * @author Jenya705
 */
@Getter
public class BukkitServerApplication extends JavaPlugin {

    private JavaServerApplication application;

    @Override
    @SneakyThrows
    public void onEnable() {
        Thread.currentThread().setContextClassLoader(
                new CompoundClassLoader(getClassLoader(), Thread.currentThread().getContextClassLoader())
        );
        application = new JavaServerApplication(new BukkitServerCore());
        application.start();
    }

    @Override
    @SneakyThrows
    public void onDisable() {
        application.stop();
    }

}
