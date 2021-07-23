package com.github.jenya705.mcapi.token;

import com.github.jenya705.mcapi.ApiPlayer;

import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

/**
 * @since 1.0
 * @author Jenya705
 */
public class ApiServerTokenGenerator implements Function<ApiPlayer, String> {

    @Override
    public String apply(ApiPlayer player) {

        // Length 85

        return (player.getUniqueId().toString() + UUID.randomUUID()).replaceAll("-", "") +
                String.format("%019d", new Date().getTime());

    }

}
