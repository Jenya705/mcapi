package com.github.jenya705.mcapi.server.module.localization;

import com.google.inject.ImplementedBy;
import net.kyori.adventure.text.Component;

/**
 * @author Jenya705
 */
@ImplementedBy(LocalizationModuleImpl.class)
public interface LocalizationModule {

    Component getLinkPermissionLocalization(String permissionName);

}
