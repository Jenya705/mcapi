package com.github.jenya705.mcapi.server.module.authorization.debug;

import com.github.jenya705.mcapi.server.application.AbstractApplicationModule;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.github.jenya705.mcapi.server.entity.BotEntity;
import com.github.jenya705.mcapi.server.util.PatternUtils;
import com.google.inject.Inject;

import java.util.Collections;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Jenya705
 */
public class DebugBotFactory extends AbstractApplicationModule {

    private static final UUID defaultUUID = new UUID(0, 0);
    private static final Pattern debugPattern = Pattern.compile("debug(\\[.*])?");

    @Inject
    public DebugBotFactory(ServerApplication application) {
        super(application);
    }

    public AbstractBot create(String token) {
        Matcher matcher;
        if (!debug() || (matcher = PatternUtils.validateAndReturnMatcher(debugPattern, token)) == null) {
            return null;
        }
        return new ContainerAbstractBot(
                defaultBotEntityBuilder().build(),
                Collections.emptyList(),
                EasyPermissionManager.ALL
        );
    }

    private static BotEntity.BotEntityBuilder defaultBotEntityBuilder() {
        return BotEntity
                .builder()
                .name("debug")
                .id(0)
                .owner(defaultUUID)
                .token("token_debug");
    }

}
