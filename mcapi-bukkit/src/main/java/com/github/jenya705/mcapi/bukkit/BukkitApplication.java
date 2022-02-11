package com.github.jenya705.mcapi.bukkit;

import com.github.jenya705.mcapi.bukkit.inventory.BukkitInventoryMoveEventHandler;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.application.guice.ApplicationBuilder;
import com.google.inject.Singleton;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;

/**
 * @author Jenya705
 */
@Getter
@Setter
@Singleton
public class BukkitApplication extends JavaPlugin {

    public static BukkitApplication instance() {
        return (BukkitApplication) BukkitUtils.getPlugin();
    }

    @Getter
    private final ApplicationBuilder applicationBuilder = new ApplicationBuilder()
            .layer(new BukkitFirstLayer(this))
            .defaultLayers()
            .layer(BukkitInventoryMoveEventHandler.class)
            .layer(BukkitServerEventHandler.class)
            ;

    private boolean asyncTab;
    private ServerApplication application;

    @Override
    public void onLoad() {
        BukkitUtils.setPlugin(this);
    }

    @Override
    public void onEnable() {
        application = applicationBuilder.build();
        application.start();
    }

    @Override
    public void onDisable() {
        application.stop();
    }

    @Override
    @SneakyThrows
    public @NotNull PluginCommand getCommand(@NotNull String name) {
        PluginCommand command = super.getCommand(name);
        if (command != null) return command;
        Constructor<PluginCommand> constructor =
                PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
        constructor.setAccessible(true);
        command = constructor.newInstance(name, this);
        Bukkit
                .getCommandMap()
                .register(getName(), command);
        return command;
    }

}
