package com.github.jenya705.mcapi.server.command.bot.permission.block;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.NamespacedKey;
import com.github.jenya705.mcapi.Vector3;
import com.github.jenya705.mcapi.block.Block;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.command.AdditionalPermissions;
import com.github.jenya705.mcapi.server.command.CommandTab;
import com.github.jenya705.mcapi.server.command.NoConfig;
import com.github.jenya705.mcapi.server.command.advanced.AdvancedCommandExecutor;
import com.github.jenya705.mcapi.server.command.bot.permission.give.PermissionGiveBotArguments;
import com.github.jenya705.mcapi.server.command.bot.permission.give.PermissionGiveBotCommand;
import com.github.jenya705.mcapi.server.entity.BotEntity;
import com.github.jenya705.mcapi.server.module.config.message.MessageContainer;
import com.github.jenya705.mcapi.world.World;
import com.google.inject.Inject;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
@NoConfig
@AdditionalPermissions("others")
public class PermissionBlockBotCommand extends AdvancedCommandExecutor<PermissionBlockBotArguments> {

    private final PermissionGiveBotCommand permissionGiveBotCommand;

    @Inject
    public PermissionBlockBotCommand(ServerApplication application,
                                     MessageContainer messageContainer,
                                     PermissionGiveBotCommand permissionGiveBotCommand) {
        super(application, messageContainer, PermissionBlockBotArguments.class);
        this.permissionGiveBotCommand = permissionGiveBotCommand;
        this
                .databaseTab((sender, permission, databaseGetter) ->
                                sender instanceof Player ?
                                        databaseGetter
                                                .getBotsByOwner(((Player) sender).getUuid())
                                                .stream()
                                                .map(BotEntity::getName)
                                                .map(CommandTab::of)
                                                .collect(Collectors.toList())
                                        : null,
                        true
                )
                .tab(() -> Collections.singletonList("<permission>"))
                .tab(() -> Arrays.asList("<is_toggled>", "true", "false"))
                .tab(() -> Collections.singletonList("<x>"))
                .tab(() -> Collections.singletonList("<y>"))
                .tab(() -> Collections.singletonList("<z>"))
                .tab(() -> core()
                        .getWorlds()
                        .stream()
                        .map(world -> world.getId().toString())
                        .collect(Collectors.toList())
                )
                .tab(() -> Arrays.asList("<is_token>", "true", "false"))
                .tab(() -> Arrays.asList("<is_regex>", "true", "false"));
    }

    @Override
    public void onCommand(CommandSender sender, PermissionBlockBotArguments args, String permission) {
        World world;
        if (args.getWorld() == null) {
            if (!(sender instanceof Player)) {
                sendMessage(sender, messageContainer().stringfulFailedToParse(6));
                return;
            }
            world = ((Player) sender).getLocation().getWorld();
        }
        else {
            world = core().getWorld(NamespacedKey.from(args.getWorld()));
        }
        if (world == null) {
            sendMessage(sender, messageContainer().worldNotFound(args.getWorld()));
            return;
        }
        PermissionGiveBotArguments giveBotArguments = new PermissionGiveBotArguments();
        giveBotArguments.setBot(args.getBot());
        giveBotArguments.setPermission(args.getPermission() + "." + args.getWorld());
        giveBotArguments.setRegex(args.isRegex());
        giveBotArguments.setTarget(Block
                .getUuid(Vector3
                        .of(args.getX(), args.getY(), args.getZ())
                )
                .toString()
        );
        giveBotArguments.setToken(args.isToken());
        giveBotArguments.setToggled(args.isToggled());
        permissionGiveBotCommand.onCommand(sender, giveBotArguments, permission);
    }
}
