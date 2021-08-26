package com.github.jenya705.mcapi;

import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;

/**
 * @author Jenya705
 */
public class BukkitApplication extends JavaPlugin implements JavaBaseCommon {

    @Override
    public void onEnable() {
        java().addClass(BukkitServerCore.class);
        java().addBean(this);
        java().start();
    }

    @Override
    public void onDisable() {
        java().stop();
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
