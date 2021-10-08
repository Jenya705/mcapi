package com.github.jenya705.mcapi.permission;

import com.github.jenya705.mcapi.Player;

/**
 * @author Jenya705
 */
public interface PermissionManagerHook {

    void givePermission(Player player, boolean toggled, String... permissions);
}
