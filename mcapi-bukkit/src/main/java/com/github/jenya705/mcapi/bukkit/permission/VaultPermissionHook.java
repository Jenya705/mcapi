package com.github.jenya705.mcapi.bukkit.permission;

import com.github.jenya705.mcapi.player.Player;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;

/**
 * @author Jenya705
 */
public class VaultPermissionHook implements PermissionManagerHook {

    private final Permission permissionVault =
            Bukkit.getServer().getServicesManager().getRegistration(Permission.class).getProvider();

    @Override
    public void givePermission(Player player, boolean toggled, String... permissions) {
        org.bukkit.entity.Player bukkitPlayer = Bukkit.getPlayer(player.getUuid());
        if (bukkitPlayer == null) return;
        for (String permission : permissions) {
            permissionVault
                    .playerAddTransient(bukkitPlayer, permission);
        }
    }
}
