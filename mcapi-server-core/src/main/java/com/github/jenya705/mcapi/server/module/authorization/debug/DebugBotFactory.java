package com.github.jenya705.mcapi.server.module.authorization.debug;

import com.github.jenya705.mcapi.server.application.AbstractApplicationModule;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.github.jenya705.mcapi.server.entity.BotEntity;
import com.github.jenya705.mcapi.server.entity.BotLinkEntity;
import com.github.jenya705.mcapi.server.module.options.RawOptionsElement;
import com.github.jenya705.mcapi.server.module.options.RawOptionsMessage;
import com.github.jenya705.mcapi.server.module.options.RawOptionsParser;
import com.github.jenya705.mcapi.server.module.storage.StorageModule;
import com.github.jenya705.mcapi.server.util.MapUtils;
import com.github.jenya705.mcapi.server.util.Pair;
import com.github.jenya705.mcapi.server.util.PatternUtils;
import com.google.inject.Inject;

import java.util.Collections;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
public class DebugBotFactory extends AbstractApplicationModule {

    private static final UUID defaultUUID = new UUID(0, 0);
    private static final Pattern debugPattern = Pattern.compile("debug(\\[(.*)])?");

    private final RawOptionsParser rawOptionsParser;
    private final StorageModule storageModule;

    @Inject
    public DebugBotFactory(ServerApplication application, StorageModule storageModule, RawOptionsParser rawOptionsParser) {
        super(application);
        this.storageModule = storageModule;
        this.rawOptionsParser = rawOptionsParser;
    }

    public AbstractBot create(String token) {
        Matcher matcher;
        if (!debug() || (matcher = PatternUtils.validateAndReturnMatcher(debugPattern, token)) == null) {
            return null;
        }
        if (matcher.group(2) != null) {
            String message = matcher.group(2);
            RawOptionsMessage optionsMessage = rawOptionsParser.parse(message);
            return new ContainerAbstractBot(
                    defaultBotEntityBuilder().build(),
                    Objects.requireNonNullElse(
                                    optionsMessage
                                            .getOptions("link"),
                                    Collections.<RawOptionsElement>emptyList()
                            )
                            .stream()
                            .map(it -> it.get().split(" "))
                            .filter(it -> it.length == 2)
                            .map(it -> new BotLinkEntity(Integer.parseInt(it[0]), UUID.fromString(it[1])))
                            .collect(Collectors.toList()),
                    new DefaultPermissionManager(
                            storageModule,
                            MapUtils.concurrentHashMap(
                                    Objects.requireNonNullElse(
                                                    optionsMessage
                                                            .getOptions("perm"),
                                                    Collections.<RawOptionsElement>emptyList()
                                            )
                                            .stream()
                                            .map(RawOptionsElement::get)
                                            .map(it -> it.startsWith("-") ?
                                                    new Pair<>(it.substring(1), false) :
                                                    new Pair<>(it, true)
                                            )
                                            .collect(Collectors.toList())
                            )
                    )
            );
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
