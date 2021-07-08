package com.github.jenya705.mcapi;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
@Setter(AccessLevel.PROTECTED)
public class MCApiBukkit extends JavaPlugin {

    private JavaServerApplication application;

    @Override
    public void onLoad() {
        setApplication(new JavaServerApplication(BukkitServerCore.class));
    }

    @Override
    public void onEnable() {
        getApplication().run();
    }

    @Override
    public void onDisable() {

    }
}
