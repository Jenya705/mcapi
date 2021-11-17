package com.github.jenya705.mcapi.module.localization;

import com.github.jenya705.mcapi.AbstractApplicationModule;
import com.github.jenya705.mcapi.OnDisable;
import com.github.jenya705.mcapi.OnInitializing;
import com.github.jenya705.mcapi.module.config.ConfigModule;
import com.github.jenya705.mcapi.permission.DefaultPermission;
import com.github.jenya705.mcapi.permission.Permissions;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Jenya705
 */
@Slf4j
public class LocalizationModuleImpl extends AbstractApplicationModule implements LocalizationModule {

    private LocalizationModuleConfig config;

    @OnInitializing
    public void initialize() {
        load();
        linkPermissionLocalization(Permissions.PLAYER_GET, "Get your player object");
        linkPermissionLocalization(Permissions.PLAYER_HAS_PERMISSION, "Check if you have permission");
        linkPermissionLocalization(Permissions.LINK_REQUEST, "Linking with you");
        linkPermissionLocalization(Permissions.PLAYER_KICK, "Kick you");
        linkPermissionLocalization(Permissions.PLAYER_BAN, "Ban you");
        linkPermissionLocalization(Permissions.PLAYER_SEND_MESSAGE, "Send message to you");
        linkPermissionLocalization(Permissions.PLAYER_GET_LOCATION, "Get your location");
        linkPermissionLocalization(Permissions.PLAYER_INVENTORY_GET, "Get your inventory (not items)");
        linkPermissionLocalization(Permissions.PLAYER_ITEM_GET, "Get items from your inventory");
        linkPermissionLocalization(Permissions.PLAYER_ENDER_CHEST_GET, "Get your ender chest (not items)");
        linkPermissionLocalization(Permissions.PLAYER_ENDER_CHEST_ITEM_GET, "Get items from your ender chest");
        save();
        debugValidate();
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

    private void debugValidate() {
        if (!app().isDebug()) return;
        for (DefaultPermission permission: DefaultPermission.values()) {
            if (permission.isGlobal()) continue;
            if (getLinkPermissionLocalization(permission.getName()) == null) {
                log.warn("Local permission {} does not have localization", permission.getName());
            }
        }
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
