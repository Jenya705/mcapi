package com.github.jenya705.mcapi.bukkit;

import com.github.jenya705.mcapi.bukkit.menu.BukkitMenuManagerImpl;
import com.github.jenya705.mcapi.bukkit.permission.LuckPermsHook;
import com.github.jenya705.mcapi.bukkit.permission.VaultPermissionHook;
import com.github.jenya705.mcapi.bukkit.utils.FailureOperation;
import com.github.jenya705.mcapi.server.ServerApplication;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.util.function.Supplier;

/**
 * @author Jenya705
 */
@Getter
@Setter
public class BukkitApplication extends JavaPlugin implements Supplier<ServerApplication> {

    public static BukkitApplication instance() {
        return (BukkitApplication) BukkitUtils.getPlugin();
    }

    private boolean asyncTab;

    private ServerApplication application;

    @Override
    public void onLoad() {
        BukkitUtils.setPlugin(this);
        application = new ServerApplication();
        application.addClasses(
                BukkitServerCore.class,
                BukkitServerEventHandler.class,
                BukkitOfflinePlayerStorageImpl.class,
                BukkitMenuManagerImpl.class
        );
        paperFeatures();
        application.addBean(this);
    }

    @Override
    public void onEnable() {
        permissionManager();
        application.start();
    }

    private void permissionManager() {
        ifClassExistsAddClass("net.luckperms.api.LuckPerms", LuckPermsHook.class)
                .ifFailed(() -> {
                    if (getServer().getPluginManager().getPlugin("Vault") == null) {
                        return false;
                    }
                    if (getServer().getServicesManager().getRegistration(Permission.class) == null) {
                        return false;
                    }
                    application.addClass(VaultPermissionHook.class);
                    return true;
                });
    }

    private void paperFeatures() {
        ifClassExistsAddClass("com.destroystokyo.paper.event.server.AsyncTabCompleteEvent", PaperAsyncTabListener.class);
    }

    private FailureOperation ifClassExistsAddClass(String className, Class<?> clazzToAdd) {
        try {
            Class.forName(className);
            application.addClass(clazzToAdd);
        } catch (ClassNotFoundException e) {
            return FailureOperation.failed();
        }
        return FailureOperation.success();
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

    /**
     * @return Server application belong to this bukkit plugin
     */
    @Override
    public ServerApplication get() {
        return application;
    }
}
