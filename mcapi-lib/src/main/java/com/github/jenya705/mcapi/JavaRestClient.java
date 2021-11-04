package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.inventory.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Jenya705
 */
public interface JavaRestClient extends RestClient {

    @Override
    Mono<? extends JavaPlayer> getPlayer(PlayerID playerID);

    @Override
    Flux<? extends JavaPlayer> getOnlinePlayers();

    @Override
    Mono<? extends JavaItemStack> getBlockInventoryItem(String world, int x, int y, int z, int item);

    @Override
    Mono<? extends JavaItemStack> getPlayerInventoryItem(PlayerID playerID, int item);

    @Override
    Mono<? extends JavaInventory> getBlockInventory(String world, int x, int y, int z);

    @Override
    Mono<? extends JavaPlayerInventory> getPlayerInventory(PlayerID playerID);
}
