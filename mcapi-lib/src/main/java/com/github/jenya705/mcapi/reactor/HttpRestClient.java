package com.github.jenya705.mcapi.reactor;

import com.github.jenya705.mcapi.*;
import com.github.jenya705.mcapi.app.LibraryApplication;
import com.github.jenya705.mcapi.command.Command;
import com.github.jenya705.mcapi.entity.*;
import com.github.jenya705.mcapi.entity.api.EntityError;
import com.github.jenya705.mcapi.entity.api.EntityLocation;
import com.github.jenya705.mcapi.entity.api.EntityPermission;
import com.github.jenya705.mcapi.selector.BotSelector;
import com.github.jenya705.mcapi.selector.OfflinePlayerSelector;
import com.github.jenya705.mcapi.selector.PlayerSelector;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufMono;
import reactor.netty.http.client.HttpClient;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

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
                        .restClient(this)
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
    public Mono<Void> sendMessage(PlayerSelector selector, Message message) {
        return voidMono(makeRequestWithBody(Routes.SEND_MESSAGE, message, selector.asString()));
    }

    @Override
    public Flux<Player> getOnlinePlayers() {
        return makeRequest(Routes.PLAYER_LIST)
                .map(it -> application.fromJson(it, RestPlayerList.class))
                .map(RestPlayerList::getUuids)
                .flatMapMany(Flux::just)
                .map(it -> LazyPlayer
                        .builder()
                        .restClient(this)
                        .uuid(it)
                        .build()
                );
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
                .map(it -> LazyOfflinePlayer
                        .builder()
                        .restClient(this)
                        .uuid(it)
                        .build()
                );
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
                .map(it -> LazyOfflinePlayer
                        .builder()
                        .restClient(this)
                        .uuid(it.getUuid())
                        .name(it.getName())
                        .online(it.isOnline())
                        .build()
                );
    }

    @Override
    public Mono<Void> banOfflinePlayer(OfflinePlayerSelector selector, Message message) {
        return voidMono(makeRequestWithBody(Routes.OFFLINE_PLAYER_BAN, message, selector.asString()));
    }

    @Override
    public Mono<Void> stop() {
        return Mono.fromFuture(
                CompletableFuture.runAsync(blockingThread::terminate)
        );
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
