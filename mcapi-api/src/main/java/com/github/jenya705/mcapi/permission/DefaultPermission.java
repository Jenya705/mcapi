package com.github.jenya705.mcapi.permission;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jenya705
 */
@Getter
@AllArgsConstructor
public enum DefaultPermission {

    USER_GET(Permissions.USER_GET, false, false, true),
    USER_GET_LOCATION(Permissions.USER_GET_LOCATION, false, false, true),
    USER_LIST(Permissions.USER_LIST, true, false, true),
    USER_HAS_PERMISSION(Permissions.USER_HAS_PERMISSION, false, false, true),
    GATEWAY_MESSAGE(Permissions.GATEWAY_MESSAGE, true, false, true),
    GATEWAY_COMMAND(Permissions.GATEWAY_COMMAND_INTERACTION, true, false, true),
    GATEWAY_LINK(Permissions.GATEWAY_LINK, true, false, true),
    GATEWAY_UNLINK(Permissions.GATEWAY_UNLINK, true, false, true),
    GATEWAY_JOIN(Permissions.GATEWAY_JOIN, true, false, true),
    GATEWAY_QUIT(Permissions.GATEWAY_QUIT, true, false, true),
    LINK_REQUEST(Permissions.LINK_REQUEST, true, false, true),
    USER_COMMAND_CREATE(Permissions.USER_COMMAND_CREATE, true, false, true),
    USER_KICK(Permissions.USER_KICK, false, true, false),
    USER_BAN(Permissions.USER_BAN, false, true, false),
    USER_SEND_MESSAGE(Permissions.USER_SEND_MESSAGE, false, true, true);

    private final String name;
    private final boolean global;
    private final boolean selector;
    private final boolean enabledDefault;
}
