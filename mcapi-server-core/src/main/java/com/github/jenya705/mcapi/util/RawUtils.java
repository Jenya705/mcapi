package com.github.jenya705.mcapi.util;

import com.github.jenya705.mcapi.ApiCommandSender;
import com.github.jenya705.mcapi.ApiError;
import com.github.jenya705.mcapi.ApiOfflinePlayer;
import com.github.jenya705.mcapi.ApiPlayer;
import lombok.AllArgsConstructor;
import lombok.experimental.Delegate;
import lombok.experimental.UtilityClass;

/**
 * @author Jenya705
 */
@UtilityClass
public class RawUtils {

    @AllArgsConstructor
    static class RawPlayer implements ApiPlayer {
        @Delegate
        private final ApiPlayer player;
    }

    @AllArgsConstructor
    static class RawOfflinePlayer implements ApiOfflinePlayer {
        @Delegate
        private final ApiOfflinePlayer player;
    }

    @AllArgsConstructor
    static class RawCommandSender implements ApiCommandSender {
        @Delegate
        private final ApiCommandSender commandSender;
    }

    public ApiPlayer raw(ApiPlayer player) {
        return new RawPlayer(player);
    }

    public ApiOfflinePlayer raw(ApiOfflinePlayer player) {
        return new RawOfflinePlayer(player);
    }

    public ApiCommandSender raw(ApiCommandSender sender) {
        return new RawCommandSender(sender);
    }

}
