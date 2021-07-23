package com.github.jenya705.mcapi;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @since 1.0
 * @author Jenya705
 */
@AllArgsConstructor
@Getter
public enum DefaultApiPermission implements ApiPermission {

    USER_GET("user.get", true),
    USER_SEND_MESSAGE("user.message.send", false)
    ;

    private final String name;
    private final boolean enabledByDefault;

}
