package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.command.Command;
import com.github.jenya705.mcapi.selector.BotSelector;
import com.github.jenya705.mcapi.selector.OfflinePlayerSelector;
import com.github.jenya705.mcapi.selector.PlayerSelector;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * @author Jenya705
 */
public interface RestClient {

    Mono<Player> getPlayer(PlayerID id);

    Mono<Location> getPlayerLocation(PlayerID id);

    Mono<Boolean> sendMessage(PlayerSelector selector, Message message);

    Flux<Player> getOnlinePlayers();

    Mono<Boolean> banPlayers(PlayerSelector selector, Message message);

    Mono<Boolean> kickPlayers(PlayerSelector selector, Message message);

    Mono<Permission> getPlayerPermission(PlayerID id, String permissionName);

    Flux<OfflinePlayer> getLinkedPlayers(BotSelector selector);

    Mono<Permission> getBotPermission(BotSelector selector, String permissionName);

    Mono<Permission> getBotPermission(BotSelector selector, String permissionName, UUID target);

    Mono<Boolean> createCommand(Command command);

    Mono<Boolean> deleteCommand(String... path);

    Mono<Boolean> requestLink(PlayerID id, LinkRequest request);

    Mono<OfflinePlayer> getOfflinePlayer(PlayerID id);

    Mono<Boolean> banOfflinePlayer(OfflinePlayerSelector selector, Message message);

}
