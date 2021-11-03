package com.github.jenya705.mcapi.module.localization;

import com.github.jenya705.mcapi.AbstractApplicationModule;
import com.github.jenya705.mcapi.OnDisable;
import com.github.jenya705.mcapi.OnInitializing;
import com.github.jenya705.mcapi.module.config.ConfigModule;
import lombok.SneakyThrows;

/**
 * @author Jenya705
 */
public class LocalizationModuleImpl extends AbstractApplicationModule implements LocalizationModule {

    private LocalizationModuleConfig config;

    @OnInitializing
    public void initialize() {
        load();
        linkPermissionLocalization("user.get", "Get your player object");
        linkPermissionLocalization("user.has_permission", "Check if you have permission");
        linkPermissionLocalization("link.request", "Linking with you");
        linkPermissionLocalization("user.kick", "Kick you");
        linkPermissionLocalization("user.ban", "Ban you");
        linkPermissionLocalization("user.send_message", "Send message to you");
        linkPermissionLocalization("user.get.location", "Get your location");
        save();
    }

    @OnDisable(priority = 4)
    public void disable() {
        save();
    }

    @SneakyThrows
    private void load() {
        config = new LocalizationModuleConfig(
                bean(ConfigModule.class).createConfig(
                        core().loadConfig("localization")
                )
        );
    }

    @SneakyThrows
    private void save() {
        core().saveConfig("localization", config.represent());
    }

    @Override
    public String getLinkPermissionLocalization(String permissionName) {
        return config.getLinkPermissions().getOrDefault(permissionName, null);
    }

    @Override
    public void setLinkPermissionLocalization(String permissionName, String value) {
        config.getLinkPermissions().put(permissionName, value);
    }

    private void linkPermissionLocalization(String permissionName, String value) {
        if (!config.getLinkPermissions().containsKey(permissionName)) {
            setLinkPermissionLocalization(permissionName, value);
        }
    }
}
