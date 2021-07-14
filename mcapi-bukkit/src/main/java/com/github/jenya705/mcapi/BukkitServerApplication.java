package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.common.CompoundClassLoader;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Jenya705
 */
@Getter
@Setter(AccessLevel.PROTECTED)
public class BukkitServerApplication extends JavaPlugin {

    private ConfigurableApplicationContext context;
    private SpringApplication application;

    @SneakyThrows
    @Override
    public void onEnable() {
        Thread.currentThread().setContextClassLoader(
                new CompoundClassLoader(getClassLoader(), Thread.currentThread().getContextClassLoader())
        );
        setContext(SpringApplication.run(JavaServerApplication.class));
    }

    @Override
    public void onDisable() {
        getContext().close();
    }
}
