package com.github.jenya705.mcapi.server.module.localization;

import com.github.jenya705.mcapi.server.application.AbstractApplicationModule;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.kyori.adventure.text.Component;

/**
 * @author Jenya705
 */
@Slf4j
public class LocalizationModuleImpl extends AbstractApplicationModule implements LocalizationModule {

    @Inject
    public LocalizationModuleImpl(ServerApplication application) {
        super(application);
    }

    @Override
    public Component getLinkPermissionLocalization(String permissionName) {
        return Component
                .translatable("mcapi.permission." + permissionName);
    }
}
