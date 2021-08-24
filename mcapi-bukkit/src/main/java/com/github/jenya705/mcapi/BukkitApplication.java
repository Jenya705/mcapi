package com.github.jenya705.mcapi;

import org.bukkit.plugin.java.JavaPlugin;

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

}
