package com.github.jenya705.mcapi.reactor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jenya705.mcapi.*;
import com.github.jenya705.mcapi.command.Command;
import com.github.jenya705.mcapi.entity.LazyPlayer;
import com.github.jenya705.mcapi.entity.RestLocation;
import com.github.jenya705.mcapi.entity.RestPlayer;
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

    private final ObjectMapper mapper = new ObjectMapper();

    public HttpRestClient(String ip, int port, String token) {
        httpClient = HttpClient
                .create()
                .host(ip)
                .port(port)
                .headers(it -> it.add("Authorization", "Bot " + token));
    }

    @Override
    public Mono<Player> getPlayer(PlayerID id) {
        return makeRequest(Routes.PLAYER, id.getId())
                .map(it -> fromJson(it, RestPlayer.class))
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
                .map(it -> fromJson(it, RestLocation.class))
                .map(it -> new EntityLocation(
                        it.getX(),
                        it.getY(),
                        it.getZ(),
                        it.getWorld()
                ));
    }

    @Override
    public Mono<Void> sendMessage(PlayerSelector selector, Message message) {
        return null;
    }

    @Override
    public Flux<Player> getOnlinePlayers() {
        return null;
    }

    @Override
    public Mono<Void> banPlayers(PlayerSelector selector, Message message) {
        return null;
    }

    @Override
    public Mono<Void> kickPlayers(PlayerSelector selector, Message message) {
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
    public Mono<Void> createCommand(Command command) {
        return null;
    }

    @Override
    public Mono<Void> deleteCommand(String... path) {
        return null;
    }

    @Override
    public Mono<Void> requestLink(PlayerID id, LinkRequest request) {
        return null;
    }

    @Override
    public Mono<OfflinePlayer> getOfflinePlayer(PlayerID id) {
        return null;
    }

    @Override
    public Mono<Void> banOfflinePlayer(OfflinePlayerSelector selector, Message message) {
        return null;
    }

    @SneakyThrows
    public <T> T fromJson(String json, Class<? extends T> type) {
        return mapper.readValue(json, type);
    }

    @SneakyThrows
    public String asJson(Object obj) {
        return mapper.writeValueAsString(obj);
    }

    public Mono<String> makeRequest(Route route, Object... args) {
        return httpClient
                .request(ReactorNettyUtils.wrap(route.getMethod()))
                .uri(ReactorNettyUtils.formatUri(route.getUri(), args))
                .responseContent()
                .aggregate()
                .asString();
    }

    public Mono<String> makeRequestWithBody(Route route, Object body, Object... args) {
        return httpClient
                .request(ReactorNettyUtils.wrap(route.getMethod()))
                .uri(ReactorNettyUtils.formatUri(route.getUri(), args))
                .send(ByteBufMono.fromString(
                        Mono.just(asJson(body))
                ))
                .responseContent()
                .aggregate()
                .asString();
    }

}
