package com.github.jenya705.mcapi.permission;

import com.github.jenya705.mcapi.ApiPlayer;

/**
 * @author Jenya705
 */
public interface PermissionManagerHook {

    void givePermission(ApiPlayer player, boolean toggled, String... permissions);

}
