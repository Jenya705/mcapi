package com.github.jenya705.mcapi.util;

import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.ServerCore;
import com.github.jenya705.mcapi.error.PlayerIdFormatException;
import lombok.experimental.UtilityClass;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Jenya705
 */
@UtilityClass
public class PlayerUtils {

    public Optional<ApiPlayer> getPlayer(String name, ServerCore core) {
        if (name.length() < 17) {
            if (name.length() < 3) {
                throw new PlayerIdFormatException(name);
            }
            return core.getOptionalPlayer(name);
        }
        else if (name.length() == 32) {
            return core.getOptionalPlayer(
                    UUID.fromString(
                            name.replaceFirst(
                                    "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5"
                            )
                    )
            );
        }
        else if (name.length() == 36) {
            return core.getOptionalPlayer(UUID.fromString(name));
        }
        else {
            throw new PlayerIdFormatException(name);
        }
    }

}
