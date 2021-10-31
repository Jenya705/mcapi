package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.block.Block;
import com.github.jenya705.mcapi.block.BlockData;
import com.github.jenya705.mcapi.command.Command;
import com.github.jenya705.mcapi.selector.BotSelector;
import com.github.jenya705.mcapi.selector.OfflinePlayerSelector;
import com.github.jenya705.mcapi.selector.PlayerSelector;
import com.github.jenya705.mcapi.world.World;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * @author Jenya705
 */
public interface RestClient {

    Mono<? extends Player> getPlayer(PlayerID id);

    Mono<? extends Location> getPlayerLocation(PlayerID id);

    Mono<? extends Void> sendMessage(PlayerSelector selector, Message message);

    Flux<? extends Player> getOnlinePlayers();

    Mono<? extends Void> banPlayers(PlayerSelector selector, Message message);

    Mono<? extends Void> kickPlayers(PlayerSelector selector, Message message);

    Mono<? extends Permission> getPlayerPermission(PlayerID id, String permissionName);

    Flux<? extends OfflinePlayer> getLinkedPlayers(BotSelector selector);

    Mono<? extends Permission> getBotPermission(BotSelector selector, String permissionName);

    Mono<? extends Permission> getBotPermission(BotSelector selector, String permissionName, UUID target);

    Mono<? extends Void> createCommand(Command command);

    Mono<? extends Void> deleteCommand(String... path);

    Mono<? extends Void> requestLink(PlayerID id, LinkRequest request);

    Mono<? extends OfflinePlayer> getOfflinePlayer(PlayerID id);

    Mono<? extends Void> banOfflinePlayer(OfflinePlayerSelector selector, Message message);

    Mono<? extends World> getWorld(String name);

    Mono<? extends Block> getBlock(String world, int x, int y, int z);

    Mono<? extends Void> stop();

    Mono<? extends BlockData> getBlockData(String world, int x, int y, int z, String blockType);

}
