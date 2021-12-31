package com.github.jenya705.mcapi.bukkit.permission;

import com.github.jenya705.mcapi.player.Player;

/**
 * @author Jenya705
 */
public interface PermissionManagerHook {

    void givePermission(Player player, boolean toggled, String... permissions);
}
