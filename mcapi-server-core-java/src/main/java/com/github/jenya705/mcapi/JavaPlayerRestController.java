package com.github.jenya705.mcapi;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.Objects;
import java.util.UUID;

/**
 * @author Jenya705
 */
@RestController("/player")
@Setter(AccessLevel.PROTECTED)
@Getter(AccessLevel.PROTECTED)
public class JavaPlayerRestController {

    private JavaServerCore core;

    public JavaPlayerRestController(JavaServerCore core) {
        setCore(core);
    }

    public JavaPlayer getPlayer(String id) {
        JavaPlayer player;
        if (id.length() == 36) {
            player = getCore().getPlayer(UUID.fromString(id));
        }
        else if (id.length() == 32) {
            player = getCore().getPlayer(UUID.fromString(id.replaceFirst(
                    "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5"
            )));
        }
        else {
            player = getCore().getPlayer(id);
        }
        return player;
    }

    @GetMapping("/{id}")
    public @NotNull ServerResponse getPlayer(ServerRequest serverRequest) {
        String name = serverRequest.pathVariable("id");
        JavaPlayer player = getPlayer(name);
        return ServerResponse.ok().body(
                Objects.requireNonNullElseGet(
                        player,
                        () -> JavaErrorUtils.playerNotFound(name)
                )
        );
    }

    @PutMapping("/{id}/send")
    public @NotNull ServerResponse sendMessage(ServerRequest serverRequest) throws Exception {
        String name = serverRequest.pathVariable("id");
        JavaPlayer player = getPlayer(name);
        if (player == null) return ServerResponse.ok().body(JavaErrorUtils.playerNotFound(name));
        player.sendMessage(GsonComponentSerializer.gson().deserialize(serverRequest.body(String.class)));
        return ServerResponse.ok().build();
    }

}
