package com.github.jenya705.mcapi.bukkit;

import com.github.jenya705.mcapi.bukkit.menu.BukkitMenuManager;
import com.github.jenya705.mcapi.bukkit.permission.LuckPermsHook;
import com.github.jenya705.mcapi.bukkit.permission.PermissionManagerHook;
import com.github.jenya705.mcapi.bukkit.permission.VaultPermissionHook;
import com.github.jenya705.mcapi.bukkit.utils.FailureOperation;
import com.github.jenya705.mcapi.server.ServerCore;
import com.google.inject.AbstractModule;
import lombok.RequiredArgsConstructor;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;

@RequiredArgsConstructor
public class BukkitFirstLayer extends AbstractModule {

    private final BukkitApplication plugin;

    @Override
    protected void configure() {
        bind(BukkitApplication.class).toInstance(plugin);
        bind(BukkitEasyCore.class);
        bind(BukkitOfflinePlayerStorage.class);
        bind(BukkitMenuManager.class);
        permissionManager();
        bind(ServerCore.class).to(BukkitServerCore.class);
        paperFeatures();
    }

    private void permissionManager() {
        ifClassExistsAddClass("net.luckperms.api.LuckPerms", LuckPermsHook.class, PermissionManagerHook.class)
                .ifFailed(() -> {
                    if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
                        return false;
                    }
                    if (Bukkit.getServer().getServicesManager().getRegistration(Permission.class) == null) {
                        return false;
                    }
                    bind(PermissionManagerHook.class).to(VaultPermissionHook.class);
                    return true;
                });
    }

    private void paperFeatures() {
        ifClassExistsAddClass("com.destroystokyo.paper.event.server.AsyncTabCompleteEvent", PaperAsyncTabListener.class);
    }

    private FailureOperation ifClassExistsAddClass(String className, Class<?> clazzToAdd) {
        try {
            Class.forName(className);
            bind(clazzToAdd);
        } catch (ClassNotFoundException e) {
            return FailureOperation.failed();
        }
        return FailureOperation.success();
    }

    private <T> FailureOperation ifClassExistsAddClass(String className, Class<? extends T> clazzToAdd, Class<T> bindTo) {
        try {
            Class.forName(className);
            bind(bindTo).to(clazzToAdd);
        } catch (ClassNotFoundException e) {
            return FailureOperation.failed();
        }
        return FailureOperation.success();
    }

}
