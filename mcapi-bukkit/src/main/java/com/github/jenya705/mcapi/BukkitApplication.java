package com.github.jenya705.mcapi;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Jenya705
 */
@Getter
public class BukkitApplication extends JavaPlugin {

    private ServerApplication application;

    @Override
    public void onEnable() {
        application = new ServerApplication(new BukkitServerCore());
        application.start();
    }

    @Override
    public void onDisable() {
        application.stop();
    }
}
