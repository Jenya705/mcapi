package com.github.jenya705.mcapi.permission;

import com.github.jenya705.mcapi.ApiPlayer;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * @author Jenya705
 */
public class VaultPermissionHook implements PermissionManagerHook {

    private final Permission permissionVault =
            Bukkit.getServer().getServicesManager().getRegistration(Permission.class).getProvider();

    @Override
    public void givePermission(ApiPlayer player, boolean toggled, String... permissions) {
        Player bukkitPlayer = Bukkit.getPlayer(player.getUuid());
        if (bukkitPlayer == null) return;
        for (String permission : permissions) {
            permissionVault
                    .playerAddTransient(bukkitPlayer, permission);
        }
    }
}
