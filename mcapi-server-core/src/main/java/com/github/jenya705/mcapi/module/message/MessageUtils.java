package com.github.jenya705.mcapi.module.message;

import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.error.BodyIsEmptyException;
import com.github.jenya705.mcapi.error.MessageTypeNotSupportException;
import com.github.jenya705.mcapi.module.web.Request;
import com.github.jenya705.mcapi.util.ReactiveUtils;
import lombok.experimental.UtilityClass;

import java.util.function.Function;

/**
 * @author Jenya705
 */
@UtilityClass
public class MessageUtils {

    public void ban(ApiPlayer player, Request request) {
        doWithMessage(player, request, it -> it.ban(player));
    }

    public void kick(ApiPlayer player, Request request) {
        doWithMessage(player, request, it -> it.kick(player));
    }

    public void doWithMessage(ApiPlayer player, Request request, Function<TypedMessage, Boolean> function) {
        doWithMessage(
                player,
                request
                        .body(TypedMessage.class)
                        .orElseThrow(BodyIsEmptyException::new),
                function
        );
    }

    public void ban(ApiPlayer player, TypedMessage message) {
        doWithMessage(player, message, it -> it.ban(player));
    }

    public void kick(ApiPlayer player, TypedMessage message) {
        doWithMessage(player, message, it -> it.kick(player));
    }

    public void doWithMessage(ApiPlayer player, TypedMessage message, Function<TypedMessage, Boolean> function) {
        ReactiveUtils.ifTrueThrow(
                function.apply(message),
                () -> new MessageTypeNotSupportException(message.getType())
        );
    }
}
