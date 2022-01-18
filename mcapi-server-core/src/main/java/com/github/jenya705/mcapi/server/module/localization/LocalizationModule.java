package com.github.jenya705.mcapi.server.module.localization;

import com.google.inject.ImplementedBy;

/**
 * @author Jenya705
 */
@ImplementedBy(LocalizationModuleImpl.class)
public interface LocalizationModule {

    String getLinkPermissionLocalization(String permissionName);

    void setLinkPermissionLocalization(String permissionName, String value);
}
