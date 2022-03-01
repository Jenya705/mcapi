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
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.command.CommandDescription;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.github.jenya705.mcapi.server.entity.BotLinkEntity;
import com.github.jenya705.mcapi.server.entity.BotPermissionEntity;
import com.github.jenya705.mcapi.server.module.command.CommandModule;
import com.github.jenya705.mcapi.server.module.config.message.MessageContainer;
import com.github.jenya705.mcapi.server.module.database.DatabaseModule;
import com.github.jenya705.mcapi.server.module.database.EventDatabaseStorage;
import com.github.jenya705.mcapi.server.module.localization.LocalizationModule;
import com.github.jenya705.mcapi.server.module.storage.StorageModule;
import com.github.jenya705.mcapi.server.util.ListUtils;
import com.github.jenya705.mcapi.server.util.MultivaluedMap;
import com.github.jenya705.mcapi.server.util.OnlinePlayerImitation;
import com.github.jenya705.mcapi.server.util.ReactiveUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Jenya705
 */
@Singleton
public class LinkingModuleImpl extends AbstractApplicationModule implements LinkingModule {

    private final LinkIgnores linkIgnores;

    private final EventDatabaseStorage databaseStorage;
    private final StorageModule storageModule;
    private final LocalizationModule localizationModule;
    private final CommandModule commandModule;
    private final MessageContainer messageContainer;

    private final MultivaluedMap<UUID, LinkObject> links = MultivaluedMap.concurrent();

    @Inject
    public LinkingModuleImpl(ServerApplication application,
                             EventDatabaseStorage databaseStorage, StorageModule storageModule,
                             LocalizationModule localizationModule, CommandModule commandModule,
                             MessageContainer messageContainer) {
        super(application);
        this.databaseStorage = databaseStorage;
        this.storageModule = storageModule;
        this.localizationModule = localizationModule;
        this.commandModule = commandModule;
        this.messageContainer = messageContainer;
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
                Arrays
                        .stream(request.getMinecraftRequestCommands())
                        .map(it -> commandModule.getBotCommandExecutor(bot, it))
                        .map(it -> it instanceof CommandDescription ? (CommandDescription) it : null)
                        .collect(Collectors.toList()),
                bot.getEntity().getId()
        );
        links.add(player.getUuid(), obj);
        sendMessage(player, obj);
    }

    public void sendMessage(Player player, LinkObject request) {
        player.sendMessage(messageContainer.render(
                messageContainer.linkRequest(
                        request,
                        localizationModule
                ),
                player
        ));
    }

    @Override
    public void unlink(int id, Player player) {
        worker().invoke(() -> {
            BotLinkEntity link = databaseStorage
                    .findLink(id, player.getUuid());
            if (link == null) return;
            databaseStorage
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
            if (enabled) {
                linkObject
                        .getOptionalPermissions()
                        .entrySet()
                        .stream()
                        .filter(Map.Entry::getValue)
                        .map(Map.Entry::getKey)
                        .forEach(it -> savePermission(linkObject, it, player));
                for (String requirePermission : linkObject.getRequest().getRequireRequestPermissions()) {
                    savePermission(linkObject, requirePermission, player);
                }
                databaseStorage
                        .save(new BotLinkEntity(
                                linkObject.getId(),
                                player.getUuid()
                        ));
            }
        });
        player.sendMessage(messageContainer.render(
                enabled ?
                        messageContainer.success() :
                        messageContainer.declined(),
                player
        ));
        if (enabled) {
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
    }

    private void validateLinkRequest(AbstractBot bot, Player player, LinkRequest request) {
        ReactiveUtils.ifTrueThrow(
                databaseStorage
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

    private void savePermission(LinkObject linkObject, String permission, Player player) {
        databaseStorage
                .upsert(new BotPermissionEntity(
                        linkObject.getId(),
                        BotPermissionEntity.toRegex(permission),
                        player.getUuid(),
                        false,
                        true
                ));
    }
}
