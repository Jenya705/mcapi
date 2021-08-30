package com.github.jenya705.mcapi.module.link;

import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.OnStartup;
import com.github.jenya705.mcapi.command.CommandsUtils;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.error.LinkRequestExistException;
import com.github.jenya705.mcapi.form.FormComponent;
import com.github.jenya705.mcapi.form.FormPlatformProvider;
import com.github.jenya705.mcapi.form.component.*;
import com.github.jenya705.mcapi.gateway.object.LinkResponseObject;
import com.github.jenya705.mcapi.link.LinkRequest;
import com.github.jenya705.mcapi.module.config.ConfigModule;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

/**
 * @author Jenya705
 */
public class LinkingModuleImpl implements LinkingModule, BaseCommon {

    private FormPlatformProvider formProvider;
    private LinkingModuleConfig config;
    private final MultivaluedMap<UUID, LinkObject> links = new MultivaluedHashMap<>();

    @OnStartup
    public void start() {
        config = new LinkingModuleConfig(
                bean(ConfigModule.class)
                        .getConfig()
                        .required("linking")
        );
        formProvider = bean(FormPlatformProvider.class);
    }

    @Override
    public void requestLink(AbstractBot bot, ApiPlayer player, LinkRequest request) {
        if (getPlayerLinks(player)
                .stream()
                .anyMatch(it -> it.getId() == bot.getEntity().getId())
        ) {
            throw new LinkRequestExistException();
        }
        LinkObject obj;
        links.add(player.getUuid(), obj = new LinkObject(
                request,
                bot,
                bot.getEntity().getId()
        ));
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
                                                                        "%permission%", it
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
                                                                                                "%permission%", permissionEntry.getKey()
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
                                                        .toArray(FormComponent[]::new)
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
        Executors.newSingleThreadExecutor().submit(() ->
                app()
                        .getGateway()
                        .getClients()
                        .stream()
                        .filter(client -> client.getEntity().getId() == finalLinkObject.getId())
                        .forEach(client -> client.send(
                                LinkResponseObject.of(
                                        !enabled,
                                        finalLinkObject
                                                .getOptionalPermissions()
                                                .entrySet()
                                                .stream()
                                                .filter(it -> !it.getValue())
                                                .map(Map.Entry::getKey)
                                                .toArray(String[]::new)
                                )
                        ))
        );
        player.sendMessage(
                CommandsUtils.placeholderMessage(
                        enabled ?
                                config.getEnabledEnd() :
                                config.getDisabledEnd()
                )
        );
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
}
