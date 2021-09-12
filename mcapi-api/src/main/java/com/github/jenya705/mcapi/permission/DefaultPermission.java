package com.github.jenya705.mcapi.permission;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jenya705
 */
@Getter
@AllArgsConstructor
public enum DefaultPermission {

    USER_GET("user.get", false, false, true),
    USER_LIST("user.list", true, false, true),
    USER_HAS_PERMISSION("user.has_permission", false, false, true),
    GATEWAY_MESSAGE_RECEIVED("gateway.message_received", true, false, true),
    GATEWAY_COMMAND("gateway.command_interaction", true, false, true),
    GATEWAY_LINK("gateway.link", true, false, true),
    GATEWAY_UNLINK("gateway.unlink", true, false, true),
    GATEWAY_JOIN("gateway.join", true, false, true),
    GATEWAY_QUIT("gateway.quit", true, false, true),
    LINK_REQUEST("link.request", true, false, true),
    USER_COMMAND_CREATE("user.command.create", true, false, true),
    USER_KICK("user.kick", false, true, false),
    USER_BAN("user.ban", false, true, false),
    USER_SEND_MESSAGE("user.send_message", false, true, true);

    private final String name;
    private final boolean global;
    private final boolean selector;
    private final boolean enabledDefault;
}
