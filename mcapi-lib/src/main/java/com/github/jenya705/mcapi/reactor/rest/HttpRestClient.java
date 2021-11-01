package com.github.jenya705.mcapi.reactor.rest;

import com.github.jenya705.mcapi.*;
import com.github.jenya705.mcapi.app.LibraryApplication;
import com.github.jenya705.mcapi.block.Block;
import com.github.jenya705.mcapi.block.BlockData;
import com.github.jenya705.mcapi.command.Command;
import com.github.jenya705.mcapi.entity.*;
import com.github.jenya705.mcapi.entity.api.EntityError;
import com.github.jenya705.mcapi.entity.api.EntityPermission;
import com.github.jenya705.mcapi.inventory.Inventory;
import com.github.jenya705.mcapi.inventory.ItemStack;
import com.github.jenya705.mcapi.inventory.PlayerInventory;
import com.github.jenya705.mcapi.reactor.ReactorNettyUtils;
import com.github.jenya705.mcapi.selector.BotSelector;
import com.github.jenya705.mcapi.selector.OfflinePlayerSelector;
import com.github.jenya705.mcapi.selector.PlayerSelector;
import com.github.jenya705.mcapi.world.World;
import lombok.Getter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufMono;
import reactor.netty.http.client.HttpClient;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * @author Jenya705
 */
public class HttpRestClient implements RestClient {

    @Getter
    private final ReactorBlockingThread blockingThread = new ReactorBlockingThread();
    private final LibraryApplication application;

    private HttpClient httpClient;

    public HttpRestClient(LibraryApplication application) {
        this.application = application;
        application.onStart(() -> {
            httpClient = HttpClient
                    .create()
                    .host(application.getIp())
                    .port(application.getPort())
                    .headers(it -> it.add("Authorization", "Bot " + application.getToken()));
            blockingThread.start();
        });
    }

    @Override
    public Mono<Player> getPlayer(PlayerID id) {
        return makeRequest(Routes.PLAYER, id.getId())
                .map(it -> application.fromJson(it, RestPlayer.class))
                .map(it -> LazyPlayer.of(this, it));
    }

    @Override
    public Mono<Location> getPlayerLocation(PlayerID id) {
        return makeRequest(Routes.PLAYER_LOCATION, id.getId())
                .map(it -> application.fromJson(it, RestLocation.class))
                .map(it -> LazyLocation.of(this, it));
    }

    @Override
    public Mono<Void> sendMessage(PlayerSelector selector, Message message) {
        return voidMono(makeRequestWithBody(Routes.SEND_MESSAGE, message, selector.asString()));
    }

    @Override
    public Flux<Player> getOnlinePlayers() {
        return makeRequest(Routes.PLAYER_LIST)
                .map(it -> application.fromJson(it, RestPlayerList.class))
                .map(RestPlayerList::getUuids)
                .flatMapMany(Flux::just)
                .map(it -> LazyPlayer.of(this, it));
    }

    @Override
    public Mono<Void> banPlayers(PlayerSelector selector, Message message) {
        return voidMono(makeRequestWithBody(Routes.BAN_PLAYER_SELECTOR, message, selector.asString()));
    }

    @Override
    public Mono<Void> kickPlayers(PlayerSelector selector, Message message) {
        return voidMono(makeRequestWithBody(Routes.KICK_PLAYER_SELECTOR, message, selector.asString()));
    }

    @Override
    public Mono<Permission> getPlayerPermission(PlayerID id, String permissionName) {
        return makeRequest(Routes.PLAYER_PERMISSION, id.getId(), permissionName)
                .map(it -> application.fromJson(it, RestPermission.class))
                .map(it -> new EntityPermission(
                        it.isToggled(),
                        it.getName(),
                        it.getTarget()
                ));
    }

    @Override
    public Flux<OfflinePlayer> getLinkedPlayers(BotSelector selector) {
        return makeRequest(Routes.BOT_LINKED, selector.asString())
                .map(it -> application.fromJson(it, RestPlayerList.class))
                .map(RestPlayerList::getUuids)
                .flatMapMany(Flux::just)
                .map(it -> LazyOfflinePlayer.of(this, it));
    }

    @Override
    public Mono<Permission> getBotPermission(BotSelector selector, String permissionName) {
        return makeRequest(Routes.BOT_PERMISSION, selector.asString(), permissionName)
                .map(it -> application.fromJson(it, RestPermission.class))
                .map(it -> new EntityPermission(
                        it.isToggled(),
                        it.getName(),
                        it.getTarget()
                ));
    }

    @Override
    public Mono<Permission> getBotPermission(BotSelector selector, String permissionName, UUID target) {
        return makeRequest(Routes.BOT_TARGET_PERMISSION, selector.asString(), permissionName, target)
                .map(it -> application.fromJson(it, RestPermission.class))
                .map(it -> new EntityPermission(
                        it.isToggled(),
                        it.getName(),
                        it.getTarget()
                ));
    }

    @Override
    public Mono<Void> createCommand(Command command) {
        return voidMono(makeRequestWithBody(Routes.COMMAND_CREATE, command));
    }

    @Override
    public Mono<Void> deleteCommand(String... path) {
        return voidMono(makeRequest(Routes.COMMAND_DELETE, String.join(":", path)));
    }

    @Override
    public Mono<Void> requestLink(PlayerID id, LinkRequest request) {
        return voidMono(makeRequestWithBody(Routes.LINK_REQUEST, request, id.getId()));
    }

    @Override
    public Mono<OfflinePlayer> getOfflinePlayer(PlayerID id) {
        return makeRequest(Routes.OFFLINE_PLAYER, id.getId())
                .map(it -> application.fromJson(it, RestOfflinePlayer.class))
                .map(it -> LazyOfflinePlayer.of(this, it));
    }

    @Override
    public Mono<Void> banOfflinePlayer(OfflinePlayerSelector selector, Message message) {
        return voidMono(makeRequestWithBody(Routes.OFFLINE_PLAYER_BAN, message, selector.asString()));
    }

    @Override
    public Mono<World> getWorld(String name) {
        return makeRequest(Routes.WORLD, name)
                .map(it -> application.fromJson(it, RestWorld.class))
                .map(it -> LazyWorld.of(this, it));
    }

    @Override
    public Mono<Block> getBlock(String world, int x, int y, int z) {
        return makeRequest(Routes.BLOCK, world, x, y, z)
                .map(it -> application.fromJson(it, RestBlock.class))
                .map(it -> LazyBlock.of(this, it));
    }

    @Override
    public Mono<? extends BlockData> getBlockData(String world, int x, int y, int z, String blockType) {
        return makeRequest(Routes.BLOCK_DATA, world, x, y, z)
                .map(it -> application.fromJson(
                        it,
                        application
                                .getBlockDataRegistry()
                                .getBlockDataClass(blockType)
                ));
    }

    @Override
    public Mono<Void> stop() {
        return Mono.fromFuture(
                CompletableFuture.runAsync(blockingThread::terminate)
        );
    }

    @Override
    public Mono<? extends ItemStack> getBlockInventoryItem(String world, int x, int y, int z, int itemX, int itemY) {
        return makeRequest(Routes.BLOCK_INVENTORY_ITEM, world, x, y, z, itemX, itemY)
                .map(it -> application.fromJson(it, RestItemStack.class))
                .map(it -> LazyItemStack.of(this, it));
    }

    @Override
    public Mono<? extends ItemStack> getPlayerInventoryItem(PlayerID playerID, int itemX, int itemY) {
        return makeRequest(Routes.PLAYER_INVENTORY_ITEM, playerID.getId(), itemX, itemY)
                .map(it -> application.fromJson(it, RestItemStack.class))
                .map(it -> LazyItemStack.of(this, it));
    }

    @Override
    public Mono<? extends Inventory> getBlockInventory(String world, int x, int y, int z) {
        return makeRequest(Routes.BLOCK_INVENTORY, world, x, y, z)
                .map(it -> application.fromJson(it, RestInventory.class))
                .map(it -> LazyBlockInventory.of(this, it, world, x, y, z));
    }

    @Override
    public Mono<? extends PlayerInventory> getPlayerInventory(PlayerID playerID) {
        return makeRequest(Routes.PLAYER_INVENTORY, playerID.getId())
                .map(it -> application.fromJson(it, RestInventory.class))
                .map(it -> LazyPlayerInventory.of(this, it, playerID));
    }

    public Mono<String> makeRequest(Route route, Object... args) {
        return blockingThread.addTask(
                httpClient
                        .request(ReactorNettyUtils.wrap(route.getMethod()))
                        .uri(ReactorNettyUtils.formatUri(route.getUri(), args))
                        .responseContent()
                        .aggregate()
                        .asString()
                        .flatMap(this::handleResponse)
        );
    }

    public Mono<String> makeRequestWithBody(Route route, Object body, Object... args) {
        return blockingThread.addTask(
                httpClient
                        .request(ReactorNettyUtils.wrap(route.getMethod()))
                        .uri(ReactorNettyUtils.formatUri(route.getUri(), args))
                        .send(ByteBufMono.fromString(
                                Mono.just(application.asJson(body))
                        ))
                        .responseContent()
                        .aggregate()
                        .asString()
                        .flatMap(this::handleResponse)
        );
    }

    private Mono<String> handleResponse(String responseValue) {
        try {
            RestError apiError = application.fromJson(responseValue, RestError.class);
            return Mono.error(application.buildException(
                    new EntityError(
                            500,
                            apiError.getCode(),
                            apiError.getNamespace(),
                            apiError.getReason()
                    )
            ));
        } catch (Throwable e) {
            return Mono.justOrEmpty(responseValue);
        }
    }

    private Mono<Void> voidMono(Mono<?> mono) {
        return mono
                .doOnError(Mono::error)
                .flatMap(it -> Mono.empty());
    }
}
