package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.common.BukkitClassLoader;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;

/**
 * @author Jenya705
 */
@Getter
public class BukkitServerApplication extends JavaPlugin {

    private SpringApplication application;
    private ConfigurableApplicationContext context;

    @Override
    @SneakyThrows
    public void onEnable() {
        Thread.currentThread().setContextClassLoader(
                new BukkitClassLoader(
                        this, getClassLoader(), Thread.currentThread().getContextClassLoader()
                )
        );
        application = new SpringApplication(JavaServerApplication.class);
        application.setResourceLoader(new DefaultResourceLoader(Thread.currentThread().getContextClassLoader()));
        context = application.run();
    }

    @Override
    public void onDisable() {
        getContext().close();
    }
}
