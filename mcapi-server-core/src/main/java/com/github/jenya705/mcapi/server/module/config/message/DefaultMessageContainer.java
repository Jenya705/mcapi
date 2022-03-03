package com.github.jenya705.mcapi.server.module.config.message;

import com.github.jenya705.mcapi.Vector3;
import com.github.jenya705.mcapi.block.Block;
import com.github.jenya705.mcapi.player.OfflinePlayer;
import com.github.jenya705.mcapi.server.ServerCore;
import com.github.jenya705.mcapi.server.command.CommandDescription;
import com.github.jenya705.mcapi.server.entity.BotEntity;
import com.github.jenya705.mcapi.server.entity.BotLinkEntity;
import com.github.jenya705.mcapi.server.entity.BotPermissionEntity;
import com.github.jenya705.mcapi.server.module.link.LinkObject;
import com.github.jenya705.mcapi.server.module.link.LinkingModule;
import com.github.jenya705.mcapi.server.module.localization.LocalizationModule;
import com.github.jenya705.mcapi.server.module.web.tunnel.EventTunnelClient;
import com.github.jenya705.mcapi.server.util.Pair;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
@Singleton
public class DefaultMessageContainer implements MessageContainer {

    private static final TextColor defaultColor = NamedTextColor.YELLOW;
    private static final TextColor signColor = NamedTextColor.GRAY;
    private static final TextColor successColor = NamedTextColor.GREEN;
    private static final TextColor errorColor = NamedTextColor.RED;
    private static final TextColor neutralColor = NamedTextColor.BLUE;

    private static final Component dash = Component.text("-").color(signColor);

    private final MessageLoader loader;
    private final ServerCore core;

    @Inject
    public DefaultMessageContainer(ServerCore core) {
        this.core = core;
        loader = new MessageLoader();
        loader.load();
    }

    @Override
    public Component commandHelp(Collection<? extends String> commands, String commandStart) {
        return Component.translatable("mcapi.command.help.header")
                .color(defaultColor)
                .append(list(
                        commands
                                .stream()
                                .map(command -> Component
                                        .text(command)
                                        .clickEvent(ClickEvent.clickEvent(
                                                ClickEvent.Action.SUGGEST_COMMAND,
                                                commandStart + command
                                        ))
                                )
                                .collect(Collectors.toList())
                ));
    }

    private static final Component notPermitted = Component
            .translatable("mcapi.notpermitted")
            .color(errorColor);

    @Override
    public Component notPermitted() {
        return notPermitted;
    }

    private static final Component success = Component
            .translatable("mcapi.success")
            .color(successColor);

    @Override
    public Component success() {
        return success;
    }

    private static final Component failedCancelled = Component
            .translatable("mcapi.failed.cancelled")
            .color(errorColor);

    @Override
    public Component failedCancelled() {
        return failedCancelled;
    }

    private static final Component failedInternal = Component
            .translatable("mcapi.failed.internal")
            .color(errorColor);

    @Override
    public Component failedInternal() {
        return failedInternal;
    }

    private static final Component badSuccess = success.color(errorColor);

    @Override
    public Component badSuccess() {
        return badSuccess;
    }

    private static final Component declined = Component
            .translatable("mcapi.declined")
            .color(errorColor);

    @Override
    public Component declined() {
        return declined;
    }

    private static final Component warning = Component
            .translatable("mcapi.warning")
            .decorate(TextDecoration.BOLD)
            .color(errorColor);

    @Override
    public Component warning() {
        return warning;
    }

    @Override
    public Component botCreation(String token) {
        return success()
                .append(Component.space())
                .append(Component
                        .translatable("mcapi.bot.create")
                        .color(defaultColor)
                        .append(Component.space())
                        .append(Component
                                .text(token)
                                .clickEvent(ClickEvent.clickEvent(
                                        ClickEvent.Action.SUGGEST_COMMAND, token
                                ))
                        )
                );
    }

    private static final Component botNotFound = Component
            .translatable("mcapi.bot.notfound")
            .color(errorColor);

    @Override
    public Component botNotFound() {
        return botNotFound;
    }

    @Override
    public Component playerNotFound(String name) {
        return Component
                .translatable("mcapi.player.notfound")
                .args(Component.text(name))
                .color(errorColor);
    }

    @Override
    public Component worldNotFound(String id) {
        return Component
                .translatable("mcapi.world.notfound")
                .args(Component.text(id))
                .color(errorColor);
    }

    private final Component onlyForPlayers = Component
            .translatable("mcapi.player.only")
            .color(errorColor);

    @Override
    public Component onlyForPlayers() {
        return onlyForPlayers;
    }

    @Override
    public Component botNameUsed() {
        return Component
                .translatable("mcapi.bot.name.used")
                .color(errorColor);
    }

    @Override
    public Component botNameTooLong() {
        return Component
                .translatable("mcapi.bot.name.toolong")
                .color(errorColor);
    }

    @Override
    public Component stringfulFailedToParse(int argumentId) {
        return Component
                .translatable("mcapi.stringful.failed.parse")
                .args(Component.text(argumentId))
                .color(errorColor);
    }

    @Override
    public Component stringfulNotEnoughArguments() {
        return Component
                .translatable("mcapi.stringful.failed.notenough")
                .color(errorColor);
    }

    @Override
    public Component botDeleteNotify() {
        return Component.empty()
                .append(warning())
                .append(Component.space())
                .append(Component
                        .translatable("mcapi.bot.delete.notify")
                        .color(defaultColor)
                );
    }

    @Override
    public Component page(int page) {
        return Component
                .translatable("mcapi.page")
                .color(defaultColor)
                .args(Component.text(page));
    }

    @Override
    public Component botList(Collection<? extends BotEntity> botEntities, String playerName, int page) {
        return Component
                .translatable("mcapi.bot.list.header")
                .args(Component.text(playerName))
                .color(defaultColor)
                .append(list(
                        botEntities
                                .stream()
                                .map(it -> Component
                                        .text(it.getName())
                                        .append(Component.space())
                                        .append(dash)
                                        .append(Component.space())
                                        .append(Component
                                                .text(it.getToken())
                                                .clickEvent(ClickEvent.suggestCommand(it.getToken()))
                                        )
                                )
                                .collect(Collectors.toList())
                ))
                .append(Component.newline())
                .append(page(page));
    }

    @Override
    public Component permissionList(Collection<? extends BotPermissionEntity> permissionEntities, String botName, int page) {
        return Component
                .translatable("mcapi.permission.list.header")
                .args(Component.text(botName))
                .color(defaultColor)
                .append(list(
                        permissionEntities
                                .stream()
                                .map(it -> Component
                                        .text(it.toLocalPermission())
                                        .color(it.isToggled() ? successColor : errorColor)
                                        .append(Component.space())
                                        .append(Component
                                                .translatable("mcapi.permission.list.target")
                                                .color(neutralColor)
                                                .hoverEvent(HoverEvent.showText((
                                                                it.isGlobal() ?
                                                                        Component
                                                                                .translatable("mcapi.permission.target.global") :
                                                                        Component.empty()
                                                                                .append(Component
                                                                                        .translatable("mcapi.permission.target.uuid")
                                                                                        .args(core
                                                                                                .getOptionalOfflinePlayer(it.getTarget())
                                                                                                .map(OfflinePlayer::getName)
                                                                                                .map(player -> (Component)
                                                                                                        Component
                                                                                                                .translatable("mcapi.permission.target.player")
                                                                                                                .args(Component.text(player))
                                                                                                )
                                                                                                .orElse(Component.text(it.getTarget().toString()))
                                                                                        )
                                                                                )
                                                                                .append(Component.newline())
                                                                                .append(Component.newline())
                                                                                .append(Component
                                                                                        .translatable("mcapi.permission.target.block")
                                                                                        .args(vector(Block.fromUuid(it.getTarget())))
                                                                                )
                                                        ).color(signColor)
                                                ))
                                        )
                                )
                                .collect(Collectors.toList())
                ))
                .append(Component.newline())
                .append(page(page));
    }

    @Override
    public Component linkList(Collection<Pair<BotLinkEntity, BotEntity>> linkEntities, String playerName, int page) {
        return Component
                .translatable("mcapi.links.list.header")
                .args(Component.text(playerName))
                .color(defaultColor)
                .append(list(
                        linkEntities
                                .stream()
                                .map(it -> Component
                                        .text(it.getRight().getName())
                                        .append(Component.space())
                                        .append(inBrackets(Component
                                                .translatable("mcapi.links.list.permissions")
                                                .color(neutralColor)
                                                .clickEvent(ClickEvent.runCommand(
                                                        "/mcapi linkMenu permission " + it.getLeft().getBotId()
                                                ))
                                        ))
                                        .append(Component.space())
                                        .append(inBrackets(Component
                                                .translatable("mcapi.links.list.unlink")
                                                .color(errorColor)
                                                .clickEvent(ClickEvent.runCommand(
                                                        "/mcapi linkMenu unlink " + it.getLeft().getBotId()
                                                ))
                                        ))
                                )
                                .collect(Collectors.toList())
                ))
                .append(Component.newline())
                .append(page(page));
    }

    @Override
    public Component eventTunnelList(Collection<? extends EventTunnelClient> eventTunnels, int page) {
        return Component
                .translatable("mcapi.eventtunnels.header")
                .color(defaultColor)
                .append(list(
                        eventTunnels
                                .stream()
                                .map(client -> Component
                                        .text(
                                                client.getOwner() == null || client.getOwner().getEntity() == null
                                                        ? "unknown" : client.getOwner().getEntity().getName()
                                        )
                                )
                                .collect(Collectors.toList())
                ))
                .append(Component.newline())
                .append(page(page));
    }

    @Override
    public Component subscriptionList(Collection<String> subscriptions, String botName, int page) {
        return Component
                .translatable("mcapi.eventtunnels.subscriptions.header")
                .args(Component.text(botName))
                .color(defaultColor)
                .append(stringList(subscriptions))
                .append(Component.newline())
                .append(page(page));
    }

    @Override
    public Component localizedPermissionList(Collection<Component> localizedPermissions, String botName) {
        return Component
                .translatable("mcapi.permission.localized.header")
                .args(Component.text(botName))
                .color(defaultColor)
                .append(list(localizedPermissions));
    }

    private final Component provideToken = Component
            .translatable("mcapi.bot.token.provide")
            .color(errorColor);

    @Override
    public Component provideToken() {
        return provideToken;
    }

    private final Component disabledByAdmin = Component
            .translatable("mcapi.disabled.admin")
            .color(errorColor);

    @Override
    public Component disabledByAdmin() {
        return disabledByAdmin;
    }

    private final Component notLinked = Component
            .translatable("mcapi.link.not")
            .color(errorColor);

    @Override
    public Component notLinked() {
        return notLinked;
    }

    private final Component notConnectedToGateway = Component
            .translatable("mcapi.gateway.notconnected")
            .color(errorColor);

    @Override
    public Component notConnectedToGateway() {
        return notConnectedToGateway;
    }

    private final Component toggleFalseText = Component
            .translatable("mcapi.link.request.button.toggle.false")
            .color(errorColor)
            .decorate(TextDecoration.UNDERLINED);

    private final Component toggleTrueText = Component
            .translatable("mcapi.link.request.button.toggle.true")
            .color(successColor)
            .decorate(TextDecoration.UNDERLINED);

    private final Component acceptButtonText = Component
            .translatable("mcapi.link.request.button.accept")
            .color(successColor);

    private final Component declineButtonText = Component
            .translatable("mcapi.link.request.button.decline")
            .color(errorColor);

    @Override
    public Component linkRequest(LinkObject request, LocalizationModule localizationModule) {
        Component result = Component
                .translatable("mcapi.link.request.title")
                .args(Component.text(request.getBot().getEntity().getName()))
                .color(defaultColor)
                .append(list(
                        Arrays
                                .stream(request.getRequest().getRequireRequestPermissions())
                                .map(localizationModule::getLinkPermissionLocalization)
                                .collect(Collectors.toList())
                ))
                .append(list(
                        request
                                .getOptionalPermissions()
                                .entrySet()
                                .stream()
                                .map(permissionEntry -> localizationModule
                                        .getLinkPermissionLocalization(permissionEntry.getKey())
                                        .append(Component.space())
                                        .append(dash)
                                        .append(Component.space())
                                        .append(
                                                (permissionEntry.getValue() ? toggleFalseText : toggleTrueText)
                                                        .clickEvent(ClickEvent.runCommand(
                                                                LinkingModule.linkCommand + " toggle " +
                                                                        request.getId() + " " + permissionEntry.getKey()
                                                        ))
                                        )
                                )
                                .collect(Collectors.toList())
                ));
        if (request.getRequest().getMinecraftRequestCommands().length != 0) {
            List<Component> commands = new ArrayList<>();
            for (int i = 0; i < request.getRequest().getMinecraftRequestCommands().length; ++i) {
                CommandDescription commandDescription = null;
                if (request.getCommandDescriptions().size() > i) {
                    commandDescription = request.getCommandDescriptions().get(i);
                }
                commands.add(Component.text(
                        request.getRequest().getMinecraftRequestCommands()[i] +
                                (commandDescription == null ? "" : " " + commandDescription.description())
                ));
            }
            result = result
                    .append(Component.newline())
                    .append(Component
                            .translatable("mcapi.link.request.minecraft.command.title")
                            .color(defaultColor)
                    )
                    .append(list(commands));
        }
        return result
                .append(Component.newline())
                .append(acceptButtonText
                        .clickEvent(ClickEvent.runCommand(
                                LinkingModule.linkCommand + " end " + request.getId() + " true"
                        ))
                )
                .append(Component.space())
                .append(declineButtonText
                        .clickEvent(ClickEvent.runCommand(
                                LinkingModule.linkCommand + " end " + request.getId() + " false"
                        ))
                );
    }

    private static Component inBrackets(Component component) {
        return Component.empty()
                .color(component.color())
                .append(Component.text("["))
                .append(component)
                .append(Component.text("]"));
    }

    private static Component vector(Vector3 vector3) {
        return inBrackets(
                Component.text(
                        vector3.getX() + ", " +
                                vector3.getY() + ", " +
                                vector3.getZ()
                )
        );
    }

    private static Component stringList(Collection<? extends String> list) {
        return stringList(list, Component.newline());
    }

    private static Component stringList(Collection<? extends String> list, Component delimiter) {
        return list(
                list
                        .stream()
                        .map(Component::text)
                        .collect(Collectors.toList()),
                delimiter
        );
    }

    private static Component list(Collection<? extends Component> list) {
        return list(list, Component.newline());
    }

    private static Component list(Collection<? extends Component> list, Component delimiter) {
        Component result = Component.empty();
        for (Component component : list) {
            result = result
                    .append(delimiter)
                    .append(dash
                            .append(Component.space())
                            .append(component.colorIfAbsent(defaultColor))
                    );
        }
        return result;
    }
}
