package com.github.jenya705.mcapi.reactor;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.jenya705.mcapi.*;
import com.github.jenya705.mcapi.app.LibraryApplication;
import com.github.jenya705.mcapi.command.Command;
import com.github.jenya705.mcapi.entity.LazyPlayer;
import com.github.jenya705.mcapi.entity.RestLocation;
import com.github.jenya705.mcapi.entity.RestPlayer;
import com.github.jenya705.mcapi.entity.RestPlayerList;
import com.github.jenya705.mcapi.entity.api.EntityLocation;
import com.github.jenya705.mcapi.selector.BotSelector;
import com.github.jenya705.mcapi.selector.OfflinePlayerSelector;
import com.github.jenya705.mcapi.selector.PlayerSelector;
import lombok.SneakyThrows;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufMono;
import reactor.netty.http.client.HttpClient;

import java.util.UUID;

/**
 * @author Jenya705
 */
public class HttpRestClient implements RestClient {

    private final HttpClient httpClient;

    private final ReactorBlockingThread blockingThread = new ReactorBlockingThread();

    private final LibraryApplication application;

    public HttpRestClient(LibraryApplication application) {
        this.application = application;
        httpClient = HttpClient
                .create()
                .host(application.getIp())
                .port(application.getPort())
                .headers(it -> it.add("Authorization", "Bot " + application.getToken()));
        blockingThread.start();
    }

    @Override
    public Mono<Player> getPlayer(PlayerID id) {
        return makeRequest(Routes.PLAYER, id.getId())
                .map(it -> application.fromJson(it, RestPlayer.class))
                .map(it -> LazyPlayer
                        .builder()
                        .client(this)
                        .uuid(it.getUuid())
                        .name(it.getName())
                        .type(it.getType())
                        .build()
                );
    }

    @Override
    public Mono<Location> getPlayerLocation(PlayerID id) {
        return makeRequest(Routes.PLAYER_LOCATION, id.getId())
                .map(it -> application.fromJson(it, RestLocation.class))
                .map(it -> new EntityLocation(
                        it.getX(),
                        it.getY(),
                        it.getZ(),
                        it.getWorld()
                ));
    }

    @Override
    public Mono<Boolean> sendMessage(PlayerSelector selector, Message message) {
        return makeRequestWithBody(Routes.SEND_MESSAGE, message, selector.asString())
                .map(it -> true);
    }

    @Override
    public Flux<Player> getOnlinePlayers() {
        return makeRequest(Routes.PLAYER_LIST)
                .map(it -> application.fromJson(it, RestPlayerList.class))
                .map(RestPlayerList::getUuids)
                .flatMapMany(Flux::just)
                .map(it -> LazyPlayer
                        .builder()
                        .client(this)
                        .uuid(it)
                        .build()
                );
    }

    @Override
    public Mono<Boolean> banPlayers(PlayerSelector selector, Message message) {
        return null;
    }

    @Override
    public Mono<Boolean> kickPlayers(PlayerSelector selector, Message message) {
        return null;
    }

    @Override
    public Mono<Permission> getPlayerPermission(PlayerID id, String permissionName) {
        return null;
    }

    @Override
    public Flux<OfflinePlayer> getLinkedPlayers(BotSelector selector) {
        return null;
    }

    @Override
    public Mono<Permission> getBotPermission(BotSelector selector, String permissionName) {
        return null;
    }

    @Override
    public Mono<Permission> getBotPermission(BotSelector selector, String permissionName, UUID target) {
        return null;
    }

    @Override
    public Mono<Boolean> createCommand(Command command) {
        return null;
    }

    @Override
    public Mono<Boolean> deleteCommand(String... path) {
        return null;
    }

    @Override
    public Mono<Boolean> requestLink(PlayerID id, LinkRequest request) {
        return null;
    }

    @Override
    public Mono<OfflinePlayer> getOfflinePlayer(PlayerID id) {
        return null;
    }

    @Override
    public Mono<Boolean> banOfflinePlayer(OfflinePlayerSelector selector, Message message) {
        return null;
    }

    public Mono<String> makeRequest(Route route, Object... args) {
        return blockingThread.addTask(
                httpClient
                        .request(ReactorNettyUtils.wrap(route.getMethod()))
                        .uri(ReactorNettyUtils.formatUri(route.getUri(), args))
                        .responseContent()
                        .aggregate()
                        .asString()
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
        );
    }
}
