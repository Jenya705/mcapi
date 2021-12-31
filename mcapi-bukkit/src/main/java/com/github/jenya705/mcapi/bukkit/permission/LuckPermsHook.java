package com.github.jenya705.mcapi.bukkit.permission;

import com.github.jenya705.mcapi.player.Player;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import net.luckperms.api.node.Node;

/**
 * @author Jenya705
 */
public class LuckPermsHook implements PermissionManagerHook {

    private final LuckPerms luckPerms = LuckPermsProvider.get();

    @Override
    public void givePermission(Player player, boolean toggled, String... permissions) {
        UserManager userManager = luckPerms.getUserManager();
        if (userManager.isLoaded(player.getUuid())) {
            givePermission(userManager.getUser(player.getUuid()), toggled, permissions);
        }
        else {
            userManager
                    .loadUser(player.getUuid())
                    .thenAccept(it -> givePermission(it, toggled, permissions));
        }
    }

    private void givePermission(User user, boolean toggled, String... permissions) {
        if (user == null) return;
        for (String permission : permissions) {
            user.data().add(Node.builder(permission).value(toggled).build());
        }
        luckPerms.getUserManager().saveUser(user);
    }
}
