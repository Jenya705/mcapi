package com.github.jenya705.mcapi.server.module.link;

import com.github.jenya705.mcapi.LinkRequest;
import com.github.jenya705.mcapi.entity.event.EntityLinkEvent;
import com.github.jenya705.mcapi.entity.event.EntityUnlinkEvent;
import com.github.jenya705.mcapi.error.*;
import com.github.jenya705.mcapi.event.QuitEvent;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.rest.event.RestLinkEvent;
import com.github.jenya705.mcapi.rest.event.RestUnlinkEvent;
import com.github.jenya705.mcapi.server.application.AbstractApplicationModule;
import com.github.jenya705.mcapi.server.application.Bean;
import com.github.jenya705.mcapi.server.application.OnStartup;
import com.github.jenya705.mcapi.server.command.CommandsUtils;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.github.jenya705.mcapi.server.entity.BotLinkEntity;
import com.github.jenya705.mcapi.server.entity.BotPermissionEntity;
import com.github.jenya705.mcapi.server.form.FormComponent;
import com.github.jenya705.mcapi.server.form.FormPlatformProvider;
import com.github.jenya705.mcapi.server.form.component.*;
import com.github.jenya705.mcapi.server.module.command.CommandModule;
import com.github.jenya705.mcapi.server.module.config.ConfigModule;
import com.github.jenya705.mcapi.server.module.database.DatabaseModule;
import com.github.jenya705.mcapi.server.module.localization.LocalizationModule;
import com.github.jenya705.mcapi.server.module.storage.StorageModule;
import com.github.jenya705.mcapi.server.util.ListUtils;
import com.github.jenya705.mcapi.server.util.MultivaluedMap;
import com.github.jenya705.mcapi.server.util.OnlinePlayerImitation;
import com.github.jenya705.mcapi.server.util.ReactiveUtils;

import java.util.*;
import java.util.stream.Stream;

/**
 * @author Jenya705
 */
public class LinkingModuleImpl extends AbstractApplicationModule implements LinkingModule {

    private LinkingModuleConfig config;
    private LinkIgnores linkIgnores;

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

    private final MultivaluedMap<UUID, LinkObject> links = MultivaluedMap.create();

    @OnStartup
    public void start() {
        config = new LinkingModuleConfig(
                bean(ConfigModule.class)
                        .getConfig()
                        .required("linking")
        );
        eventLoop()
                .handler(QuitEvent.class, event -> {
                    Player player = new OnlinePlayerImitation(event.getPlayer());
                    if (links.containsKey(player.getUuid())) {
                        links
                                .get(player.getUuid())
                                .forEach(it -> end(player, it, false));
                        links.remove(player.getUuid());
                    }
                });
        linkIgnores = new LinkIgnores(app());
        linkIgnores.init();
    }

    @Override
    public void requestLink(AbstractBot bot, Player player, LinkRequest request) {
        validateLinkRequest(bot, player, request);
        LinkObject obj = new LinkObject(
                request, bot,
                bot.getEntity().getId()
        );
        links.add(player.getUuid(), obj);
        sendMessage(player, obj);
    }

    public void sendMessage(Player player, LinkObject request) {
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
    public void unlink(int id, Player player) {
        worker().invoke(() -> {
            BotLinkEntity link = databaseModule
                    .storage()
                    .findLink(id, player.getUuid());
            if (link == null) return;
            databaseModule
                    .storage()
                    .delete(link);
            eventTunnel()
                    .getClients()
                    .stream()
                    .filter(it -> it.getOwner().getEntity().getId() == id)
                    .filter(it -> it.isSubscribed(RestUnlinkEvent.type))
                    .forEach(it -> it.send(new EntityUnlinkEvent(player)));
        });
    }

    @Override
    public boolean isExists(Player player, int index, String permission) {
        return getPlayerLinks(player)
                .stream()
                .anyMatch(linkObject -> linkObject.getId() == index);
    }

    @Override
    public void toggle(Player player, int index, String permission) {
        getPlayerLinksById(player, index)
                .forEach(linkObject -> linkObject.toggleOptionalPermission(permission));
    }

    @Override
    public void update(Player player, int index) {
        getPlayerLinksById(player, index)
                .forEach(linkObject -> sendMessage(player, linkObject));
    }

    @Override
    public void end(Player player, int index, boolean enabled) {
        List<LinkObject> playerLinks = getPlayerLinks(player);
        for (int i = 0; i < playerLinks.size(); ++i) {
            LinkObject currentPlayerLink = playerLinks.get(i);
            if (currentPlayerLink.getId() == index) {
                end(player, currentPlayerLink, enabled);
                playerLinks.remove(i);
                break;
            }
        }
    }

    private void end(Player player, LinkObject linkObject, boolean enabled) {
        worker().invoke(() -> {
            eventTunnel()
                    .getClients()
                    .stream()
                    .filter(client -> client.getOwner().getEntity().getId() == linkObject.getId())
                    .filter(client -> client.isSubscribed(RestLinkEvent.type))
                    .forEach(client -> client.send(
                            new EntityLinkEvent(
                                    !enabled,
                                    player,
                                    linkObject
                                            .getOptionalPermissions()
                                            .entrySet()
                                            .stream()
                                            .filter(it -> !it.getValue())
                                            .map(Map.Entry::getKey)
                                            .toArray(String[]::new)
                            )
                    ));
            linkObject
                    .getOptionalPermissions()
                    .entrySet()
                    .stream()
                    .filter(Map.Entry::getValue)
                    .map(Map.Entry::getKey)
                    .forEach(it -> savePermission(linkObject, it, player));
            for (String requirePermission: linkObject.getRequest().getRequireRequestPermissions()) {
                savePermission(linkObject, requirePermission, player);
            }
            databaseModule
                    .storage()
                    .save(new BotLinkEntity(
                            linkObject.getId(),
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
                                        linkObject.getBot().getEntity().getId()
                                        ) + "." + it.replace(' ', '.')
                                )
                                .toArray(String[]::new)
                );
    }

    private void validateLinkRequest(AbstractBot bot, Player player, LinkRequest request) {
        ReactiveUtils.ifTrueThrow(
                databaseModule
                        .storage()
                        .findLink(bot.getEntity().getId(), player.getUuid()) != null,
                LinkExistException::create
        );
        ReactiveUtils.ifTrueThrow(
                getPlayerLinks(player)
                        .stream()
                        .anyMatch(it -> it.getId() == bot.getEntity().getId()),
                LinkRequestExistException::create
        );
        List<String> joinedList = ListUtils.join(
                request.getRequireRequestPermissions(),
                request.getOptionalRequestPermissions()
        );
        joinedList
                .stream()
                .filter(it -> storageModule.getPermission(it) == null || linkIgnores.isIgnored(it))
                .findAny()
                .ifPresent(it -> {
                    throw LinkRequestPermissionNotFoundException.create(it);
                });
        joinedList
                .stream()
                .filter(it -> storageModule.getPermission(it).isGlobal())
                .findAny()
                .ifPresent(it -> {
                    throw LinkRequestPermissionIsGlobalException.create(it);
                });
        Arrays
                .stream(request.getMinecraftRequestCommands())
                .filter(it -> commandModule.getBotCommandExecutor(bot, it) == null)
                .findAny()
                .ifPresent(it -> {
                    throw BotCommandNotExistException.create(it);
                });
    }

    private List<LinkObject> getPlayerLinks(Player player) {
        return links
                .getOrDefault(player.getUuid(), Collections.emptyList());
    }

    private Stream<LinkObject> getPlayerLinksById(Player player, int index) {
        return getPlayerLinks(player)
                .stream()
                .filter(it -> it.getId() == index);
    }

    private static String getCommand(String command, Object... formats) {
        return linkCommand + " " + String.format(command, formats);
    }

    private void savePermission(LinkObject linkObject, String permission, Player player) {
        databaseModule
                .storage()
                .upsert(new BotPermissionEntity(
                        linkObject.getId(),
                        BotPermissionEntity.toRegex(permission),
                        player.getUuid(),
                        false,
                        true
                ));
    }
}
