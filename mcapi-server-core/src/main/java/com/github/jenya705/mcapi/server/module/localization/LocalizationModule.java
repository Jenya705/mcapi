package com.github.jenya705.mcapi.server.module.localization;

/**
 * @author Jenya705
 */
public interface LocalizationModule {

    String getLinkPermissionLocalization(String permissionName);

    void setLinkPermissionLocalization(String permissionName, String value);
}
