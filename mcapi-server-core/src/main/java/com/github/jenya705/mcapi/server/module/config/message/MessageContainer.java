package com.github.jenya705.mcapi.server.module.config.message;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.LinkRequest;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.server.entity.BotEntity;
import com.github.jenya705.mcapi.server.entity.BotLinkEntity;
import com.github.jenya705.mcapi.server.entity.BotPermissionEntity;
import com.github.jenya705.mcapi.server.module.link.LinkObject;
import com.github.jenya705.mcapi.server.module.localization.LocalizationModule;
import com.github.jenya705.mcapi.server.module.web.tunnel.EventTunnelClient;
import com.github.jenya705.mcapi.server.util.Pair;
import com.google.inject.ImplementedBy;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.translation.GlobalTranslator;

import java.util.Collection;
import java.util.Locale;
import java.util.Objects;

/**
 * @author Jenya705
 */
@ImplementedBy(DefaultMessageContainer.class)
public interface MessageContainer {

    default Component render(Component component, Locale locale) {
        return GlobalTranslator.render(component, Objects.requireNonNullElse(locale, Locale.ENGLISH));
    }

    default Component render(Component component, Player sender) {
        return render(component, sender.getLocale());
    }

    default Component render(Component component, CommandSender sender) {
        return render(component, sender instanceof Player ? ((Player) sender).getLocale() : null);
    }

    Component commandHelp(Collection<? extends String> commands, String commandStart);

    Component notPermitted();

    Component success();

    Component badSuccess();

    Component declined();

    Component warning();

    Component botCreation(String token);

    Component botNotFound();

    Component playerNotFound(String name);

    Component onlyForPlayers();

    Component botNameUsed();

    Component botNameTooLong();

    Component botDeleteNotify();

    Component stringfulFailedToParse(int argumentId);

    Component stringfulNotEnoughArguments();

    Component page(int page);

    Component botList(Collection<? extends BotEntity> botEntities, String playerName, int page);

    Component permissionList(Collection<? extends BotPermissionEntity> permissionEntities, String botName, int page);

    Component linkList(Collection<Pair<BotLinkEntity, BotEntity>> linkEntities, String playerName, int page);

    Component eventTunnelList(Collection<? extends EventTunnelClient> eventTunnels, int page);

    Component subscriptionList(Collection<String> subscriptions, String botName, int page);

    Component localizedPermissionList(Collection<String> localizedPermissions, String botName);

    Component provideToken();

    Component disabledByAdmin();

    Component notLinked();

    Component notConnectedToGateway();

    Component linkRequest(LinkObject request, LocalizationModule localizationModule);

}
