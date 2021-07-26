package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.command.BukkitCommandExecutorWrapper;
import com.github.jenya705.mcapi.common.CompoundClassLoader;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
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
        saveConfig();
        Thread.currentThread().setContextClassLoader(
                new CompoundClassLoader(getClassLoader(), Thread.currentThread().getContextClassLoader())
        );
        application = new JavaServerApplication(new BukkitServerCore(this));
        registerCommand("mcapi", new BukkitCommandExecutorWrapper(application.getMainCommand()));
        saveConfig();
        application.start();
    }

    @Override
    @SneakyThrows
    public void onDisable() {
        application.stop();
        saveConfig();
    }

    public void registerCommand(String command, Object executor) {
        PluginCommand pluginCommand = getCommand(command);
        if (pluginCommand == null) throw new RuntimeException(String.format("Can not register command %s", command));
        if (executor instanceof CommandExecutor) {
            pluginCommand.setExecutor((CommandExecutor) executor);
        }
        if (executor instanceof TabExecutor) {
            pluginCommand.setTabCompleter((TabExecutor) executor);
        }
    }

}
