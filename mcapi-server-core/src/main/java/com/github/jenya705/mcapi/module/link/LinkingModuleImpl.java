package com.github.jenya705.mcapi.module.link;

import com.github.jenya705.mcapi.AbstractApplicationModule;
import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.Bean;
import com.github.jenya705.mcapi.OnStartup;
import com.github.jenya705.mcapi.command.CommandsUtils;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.entity.BotLinkEntity;
import com.github.jenya705.mcapi.entity.BotPermissionEntity;
import com.github.jenya705.mcapi.entity.RestLinkRequest;
import com.github.jenya705.mcapi.entity.api.event.EntityLinkEvent;
import com.github.jenya705.mcapi.entity.api.event.EntityUnlinkEvent;
import com.github.jenya705.mcapi.entity.event.RestLinkEvent;
import com.github.jenya705.mcapi.entity.event.RestUnlinkEvent;
import com.github.jenya705.mcapi.error.BotCommandNotExistException;
import com.github.jenya705.mcapi.error.LinkRequestExistException;
import com.github.jenya705.mcapi.error.LinkRequestPermissionIsGlobalException;
import com.github.jenya705.mcapi.error.LinkRequestPermissionNotFoundException;
import com.github.jenya705.mcapi.form.FormComponent;
import com.github.jenya705.mcapi.form.FormPlatformProvider;
import com.github.jenya705.mcapi.form.component.*;
import com.github.jenya705.mcapi.module.command.CommandModule;
import com.github.jenya705.mcapi.module.config.ConfigModule;
import com.github.jenya705.mcapi.module.database.DatabaseModule;
import com.github.jenya705.mcapi.module.localization.LocalizationModule;
import com.github.jenya705.mcapi.module.storage.StorageModule;
import com.github.jenya705.mcapi.util.ListUtils;
import com.github.jenya705.mcapi.util.MultivaluedMap;
import com.github.jenya705.mcapi.util.MultivaluedMapImpl;
import com.github.jenya705.mcapi.util.ReactiveUtils;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

/**
 * @author Jenya705
 */
public class LinkingModuleImpl extends AbstractApplicationModule implements LinkingModule {

    private final ExecutorService async = Executors.newSingleThreadExecutor();

    private LinkingModuleConfig config;

    @Bean
    private FormPlatformProvider formProvider;
    @Bean
    private DatabaseModule databaseModule;
    @Bean
    private StorageModule storageModule;
    @Bean
    private LocalizationModule localizationModule;
    @Bean
    private CommandModule commandModule;

    private final MultivaluedMap<UUID, LinkObject> links = new MultivaluedMapImpl<>(new HashMap<>());

    @OnStartup
    public void start() {
        config = new LinkingModuleConfig(
                bean(ConfigModule.class)
                        .getConfig()
                        .required("linking")
        );
    }

    @Override
    public void requestLink(AbstractBot bot, ApiPlayer player, RestLinkRequest request) {
        validateLinkRequest(bot, player, request);
        LinkObject obj = new LinkObject(
                request, bot,
                bot.getEntity().getId()
        );
        links.add(player.getUuid(), obj);
        sendMessage(player, obj);
    }

    public void sendMessage(ApiPlayer player, LinkObject request) {
        formProvider.sendMessage(player,
                formProvider
                        .newBuilder()
                        .components(
                                config.getLinkMessageComponents().toArray(String[]::new),
                                Map.of(
                                        "newline", NewLineComponent.get(),
                                        "title", TitleComponent.of(
                                                CommandsUtils.placeholderMessage(
                                                        config.getTitle(),
                                                        "%bot_name%", request.getBot().getEntity().getName()
                                                )
                                        ),
                                        "content", ListComponent.joining(
                                                new FormComponent[]{
                                                        ContentComponent.of(CommandsUtils.listMessage(
                                                                config.getContentLayout(),
                                                                config.getContentRequiredElement(),
                                                                config.getContentDelimiter(),
                                                                Arrays.asList(request.getRequest().getRequireRequestPermissions()),
                                                                it -> new String[]{
                                                                        "%permission%", localizationModule.getLinkPermissionLocalization(it)
                                                                },
                                                                "%bot_name%", request.getBot().getEntity().getName()
                                                        ))
                                                },
                                                request
                                                        .getOptionalPermissions()
                                                        .entrySet()
                                                        .stream()
                                                        .map(permissionEntry ->
                                                                ListComponent.of(
                                                                        ContentComponent.of(
                                                                                CommandsUtils.placeholderMessage(
                                                                                        config.getContentDelimiter()
                                                                                )
                                                                        ),
                                                                        ContentComponent.of(
                                                                                CommandsUtils
                                                                                        .placeholderMessage(
                                                                                                config.getContentOptionalElement(),
                                                                                                "%permission%", localizationModule
                                                                                                        .getLinkPermissionLocalization(
                                                                                                                permissionEntry.getKey()
                                                                                                        )
                                                                                        )
                                                                        ),
                                                                        ButtonComponent.of(
                                                                                CommandsUtils
                                                                                        .placeholderMessage(
                                                                                                permissionEntry.getValue() ?
                                                                                                        config.getContentOptionalToggleFalse() :
                                                                                                        config.getContentOptionalToggleTrue()
                                                                                        ),
                                                                                getCommand(
                                                                                        "toggle %d %s",
                                                                                        request.getId(),
                                                                                        permissionEntry.getKey()
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                        .toArray(FormComponent[]::new),
                                                new FormComponent[]{
                                                        ContentComponent.of(CommandsUtils.listMessage(
                                                                config.getContentMinecraftCommandLayout(),
                                                                config.getContentMinecraftCommandElement(),
                                                                config.getContentDelimiter(),
                                                                Arrays.asList(request.getRequest().getMinecraftRequestCommands()),
                                                                it -> new String[]{
                                                                        "%command%",
                                                                        String.format(
                                                                                "%s %s", request.getBot().getEntity().getName(), it
                                                                        )
                                                                }
                                                        ))
                                                }
                                        ),
                                        "accept", ButtonComponent.of(
                                                CommandsUtils.placeholderMessage(
                                                        config.getAcceptButton()
                                                ),
                                                getCommand(
                                                        "end %d true",
                                                        request.getId()
                                                )
                                        ),
                                        "decline", ButtonComponent.of(
                                                CommandsUtils.placeholderMessage(
                                                        config.getDeclineButton()
                                                ),
                                                getCommand(
                                                        "end %d false",
                                                        request.getId()
                                                )
                                        )
                                )
                        )
                        .build()
        );
    }

    @Override
    public void unlink(int id, ApiPlayer player) {
        DatabaseModule.async.submit(() -> {
            BotLinkEntity link = databaseModule
                    .storage()
                    .findLink(id, player.getUuid());
            if (link == null) return;
            databaseModule
                    .storage()
                    .delete(link);
            async.submit(() ->
                    gateway()
                            .getClients()
                            .stream()
                            .filter(it -> it.getOwner().getEntity().getId() == id)
                            .filter(it -> it.isSubscribed(RestUnlinkEvent.type))
                            .forEach(it -> it.send(new EntityUnlinkEvent(player).rest()))
            );
        });
    }

    @Override
    public boolean isExists(ApiPlayer player, int index, String permission) {
        return getPlayerLinks(player)
                .stream()
                .anyMatch(linkObject -> linkObject.getId() == index);
    }

    @Override
    public void toggle(ApiPlayer player, int index, String permission) {
        getPlayerLinksById(player, index)
                .forEach(linkObject -> linkObject.toggleOptionalPermission(permission));
    }

    @Override
    public void update(ApiPlayer player, int index) {
        getPlayerLinksById(player, index)
                .forEach(linkObject -> sendMessage(player, linkObject));
    }

    @Override
    public void end(ApiPlayer player, int index, boolean enabled) {
        LinkObject linkObject = null;
        List<LinkObject> playerLinks = getPlayerLinks(player);
        for (int i = 0; i < playerLinks.size(); ++i) {
            LinkObject currentPlayerLink = playerLinks.get(i);
            if (currentPlayerLink.getId() == index) {
                linkObject = currentPlayerLink;
                playerLinks.remove(i);
                break;
            }
        }
        if (linkObject == null) return;
        LinkObject finalLinkObject = linkObject;
        async.submit(() ->
                gateway()
                        .getClients()
                        .stream()
                        .filter(client -> client.getOwner().getEntity().getId() == finalLinkObject.getId())
                        .filter(client -> client.isSubscribed(RestLinkEvent.type))
                        .forEach(client -> client.send(
                                new EntityLinkEvent(
                                        !enabled,
                                        player,
                                        finalLinkObject
                                                .getOptionalPermissions()
                                                .entrySet()
                                                .stream()
                                                .filter(it -> !it.getValue())
                                                .map(Map.Entry::getKey)
                                                .toArray(String[]::new)
                                ).rest()
                        ))
        );
        DatabaseModule.async.submit(() -> {
            finalLinkObject
                    .getOptionalPermissions()
                    .entrySet()
                    .stream()
                    .filter(Map.Entry::getValue)
                    .map(Map.Entry::getKey)
                    .forEach(it -> savePermission(finalLinkObject, it, player));
            Arrays.stream(
                    finalLinkObject
                            .getRequest()
                            .getRequireRequestPermissions()
            )
                    .forEach(it -> savePermission(finalLinkObject, it, player));
            databaseModule
                    .storage()
                    .save(new BotLinkEntity(
                            finalLinkObject.getId(),
                            player.getUuid()
                    ));
        });
        player.sendMessage(
                CommandsUtils.placeholderMessage(
                        enabled ?
                                config.getEnabledEnd() :
                                config.getDisabledEnd()
                )
        );
        core()
                .givePermission(
                        player,
                        true,
                        Arrays
                                .stream(linkObject.getRequest().getMinecraftRequestCommands())
                                .map(it -> String.format(
                                        CommandModule.permissionFormat,
                                        finalLinkObject.getBot().getEntity().getId()
                                        ) + "." + it.replaceAll(" ", ".")
                                )
                                .toArray(String[]::new)
                );
    }

    private void validateLinkRequest(AbstractBot bot, ApiPlayer player, RestLinkRequest request) {
        ReactiveUtils.ifTrueThrow(
                getPlayerLinks(player)
                        .stream()
                        .anyMatch(it -> it.getId() == bot.getEntity().getId()),
                LinkRequestExistException::new
        );
        List<String> joinedList = ListUtils.join(
                request.getRequireRequestPermissions(),
                request.getOptionalRequestPermissions()
        );
        joinedList
                .stream()
                .filter(it -> storageModule.getPermission(it) == null)
                .findAny()
                .ifPresent(it -> {
                    throw new LinkRequestPermissionNotFoundException(it);
                });
        joinedList
                .stream()
                .filter(it -> storageModule.getPermission(it).isGlobal())
                .findAny()
                .ifPresent(it -> {
                    throw new LinkRequestPermissionIsGlobalException(it);
                });
        Arrays
                .stream(request.getMinecraftRequestCommands())
                .filter(it -> commandModule.getBotCommandExecutor(bot, it) == null)
                .findAny()
                .ifPresent(it -> {
                    throw new BotCommandNotExistException(it);
                });
    }

    private List<LinkObject> getPlayerLinks(ApiPlayer player) {
        return links
                .getOrDefault(player.getUuid(), Collections.emptyList());
    }

    private Stream<LinkObject> getPlayerLinksById(ApiPlayer player, int index) {
        return getPlayerLinks(player)
                .stream()
                .filter(it -> it.getId() == index);
    }

    private static String getCommand(String command, Object... formats) {
        return linkCommand + " " + String.format(command, formats);
    }

    private void savePermission(LinkObject linkObject, String permission, ApiPlayer player) {
        databaseModule
                .storage()
                .upsert(new BotPermissionEntity(
                        linkObject.getId(),
                        permission,
                        player.getUuid(),
                        true
                ));
    }
}
