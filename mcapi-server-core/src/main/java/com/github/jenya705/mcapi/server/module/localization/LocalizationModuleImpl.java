package com.github.jenya705.mcapi.server.module.localization;

import com.github.jenya705.mcapi.server.application.AbstractApplicationModule;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.google.inject.Inject;
import net.kyori.adventure.text.Component;
import org.slf4j.Logger;

/**
 * @author Jenya705
 */
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
