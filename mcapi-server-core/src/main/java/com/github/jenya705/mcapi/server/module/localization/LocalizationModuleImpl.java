package com.github.jenya705.mcapi.server.module.localization;

import com.github.jenya705.mcapi.permission.DefaultPermission;
import com.github.jenya705.mcapi.permission.Permissions;
import com.github.jenya705.mcapi.server.application.*;
import com.github.jenya705.mcapi.server.module.config.ConfigModule;
import com.google.inject.Inject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author Jenya705
 */
@Slf4j
public class LocalizationModuleImpl extends AbstractApplicationModule implements LocalizationModule {

    private final LocalizationModuleConfig config;

    @Inject
    public LocalizationModuleImpl(ServerApplication application, ConfigModule configModule) throws IOException {
        super(application);
        config = new LocalizationModuleConfig(
                configModule.createConfig(
                        core().loadConfig("localization")
                )
        );
        linkPermissionLocalization(Permissions.PLAYER_GET, "Get your player object");
        linkPermissionLocalization(Permissions.PLAYER_HAS_PERMISSION, "Check if you have permission");
        linkPermissionLocalization(Permissions.LINK_REQUEST, "Linking with you");
        linkPermissionLocalization(Permissions.PLAYER_KICK, "Kick you");
        linkPermissionLocalization(Permissions.PLAYER_BAN, "Ban you");
        linkPermissionLocalization(Permissions.PLAYER_KILL, "Kill you");
        linkPermissionLocalization(Permissions.PLAYER_SEND_MESSAGE, "Send message to you");
        linkPermissionLocalization(Permissions.PLAYER_GET_LOCATION, "Get your location");
        linkPermissionLocalization(Permissions.PLAYER_INVENTORY_GET, "Get your inventory (not items)");
        linkPermissionLocalization(Permissions.PLAYER_ITEM_GET, "Get items from your inventory");
        linkPermissionLocalization(Permissions.PLAYER_ENDER_CHEST_GET, "Get your ender chest (not items)");
        linkPermissionLocalization(Permissions.PLAYER_ENDER_CHEST_ITEM_GET, "Get items from your ender chest");
        linkPermissionLocalization(Permissions.EVENT_TUNNEL_BLOCK_BREAK, "Listen to block breaking");
        linkPermissionLocalization(Permissions.EVENT_TUNNEL_BLOCK_PLACE, "Listen to block placing");
        linkPermissionLocalization(Permissions.PLAYER_OPEN_INVENTORY, "Open inventory for you");
        linkPermissionLocalization(Permissions.PLAYER_CLOSE_INVENTORY, "Close your current inventory");
        save();
        debugValidate();
    }

    @SneakyThrows
    @OnDisable(priority = 5)
    @OnStartup(priority = 5)
    private void save() {
        core().saveConfig("localization", config.represent());
    }

    private void debugValidate() {
        if (!debug()) return;
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