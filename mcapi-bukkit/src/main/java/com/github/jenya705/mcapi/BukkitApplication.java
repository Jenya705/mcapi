package com.github.jenya705.mcapi;

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
public class BukkitApplication extends JavaPlugin implements JavaBaseCommon {

    private boolean asyncTab;

    @Override
    public void onEnable() {
        java().addClass(BukkitServerCore.class);
        java().addClass(BukkitServerGateway.class);
        paperFeatures();
        java().addBean(this);
        java().start();
    }

    private void paperFeatures() {
        ifClassExistsAddClass("com.destroystokyo.paper.event.server.AsyncTabCompleteEvent", PaperAsyncTabListener.class);
    }

    private void ifClassExistsAddClass(String className, Class<?> clazzToAdd) {
        try {
            Class.forName(className);
            java().addClass(clazzToAdd);
        } catch (ClassNotFoundException e) {
            // IGNORED
        }
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
