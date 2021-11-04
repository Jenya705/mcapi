package com.github.jenya705.mcapi.reactor.rest;

import com.github.jenya705.mcapi.JavaPlayer;
import com.github.jenya705.mcapi.JavaRestClient;
import com.github.jenya705.mcapi.PlayerID;
import com.github.jenya705.mcapi.Routes;
import com.github.jenya705.mcapi.app.LibraryApplication;
import com.github.jenya705.mcapi.entity.java.LazyJavaBlockInventory;
import com.github.jenya705.mcapi.entity.java.LazyJavaItemStack;
import com.github.jenya705.mcapi.entity.java.LazyJavaPlayer;
import com.github.jenya705.mcapi.entity.java.LazyJavaPlayerInventory;
import com.github.jenya705.mcapi.inventory.*;
import com.github.jenya705.mcapi.rest.RestPlayer;
import com.github.jenya705.mcapi.rest.RestPlayerList;
import com.github.jenya705.mcapi.rest.inventory.RestInventory;
import com.github.jenya705.mcapi.rest.inventory.RestJavaItemStack;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Jenya705
 */
public class JavaHttpRestClient extends HttpRestClient implements JavaRestClient {

    public JavaHttpRestClient(LibraryApplication<? extends JavaRestClient, ?> application) {
        super(application);
    }

    @Override
    public Flux<? extends JavaPlayer> getOnlinePlayers() {
        return makeRequest(Routes.PLAYER_LIST)
                .map(it -> getApplication().fromJson(it, RestPlayerList.class))
                .map(RestPlayerList::getUuids)
                .flatMapMany(Flux::just)
                .map(it -> LazyJavaPlayer.of(this, it));
    }

    @Override
    public Mono<? extends JavaPlayer> getPlayer(PlayerID id) {
        return makeRequest(Routes.PLAYER, id.getId())
                .map(it -> getApplication().fromJson(it, RestPlayer.class))
                .map(it -> LazyJavaPlayer.of(this, it));
    }

    @Override
    public Mono<? extends JavaItemStack> getBlockInventoryItem(String world, int x, int y, int z, int item) {
        return makeRequest(Routes.BLOCK_INVENTORY_ITEM, world, x, y, z, item)
                .map(it -> getApplication().fromJson(it, RestJavaItemStack.class))
                .map(it -> LazyJavaItemStack.of(this, it));
    }

    @Override
    public Mono<? extends JavaItemStack> getPlayerInventoryItem(PlayerID playerID, int item) {
        return makeRequest(Routes.PLAYER_INVENTORY_ITEM, playerID.getId(), item)
                .map(it -> getApplication().fromJson(it, RestJavaItemStack.class))
                .map(it -> LazyJavaItemStack.of(this, it));
    }

    @Override
    public Mono<? extends JavaInventory> getBlockInventory(String world, int x, int y, int z) {
        return makeRequest(Routes.BLOCK_INVENTORY, world, x, y, z)
                .map(it -> getApplication().fromJson(it, RestInventory.class))
                .map(it -> LazyJavaBlockInventory.of(this, it, world, x, y, z));
    }

    @Override
    public Mono<? extends JavaPlayerInventory> getPlayerInventory(PlayerID playerID) {
        return makeRequest(Routes.PLAYER_INVENTORY, playerID.getId())
                .map(it -> getApplication().fromJson(it, RestInventory.class))
                .map(it -> LazyJavaPlayerInventory.of(this, it, playerID));
    }
}
