package com.github.jenya705.mcapi.command;

import com.github.jenya705.mcapi.ApiSender;
import com.github.jenya705.mcapi.BukkitPlayerWrapper;
import com.github.jenya705.mcapi.BukkitSenderWrapper;
import com.github.jenya705.mcapi.common.ArrayIterator;
import lombok.AllArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @since 1.0
 * @author Jenya705
 */
@AllArgsConstructor
public class BukkitCommandExecutorWrapper implements CommandExecutor, TabExecutor {

    private final ApiServerCommandExecutor commandExecutor;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        commandExecutor.execute(wrapSender(sender), new ArrayIterator<>(args));
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return commandExecutor.possibleVariants(wrapSender(sender), new ArrayIterator<>(args));
    }

    public static ApiSender wrapSender(CommandSender commandSender) {
        if (commandSender instanceof Player) return new BukkitPlayerWrapper((Player) commandSender);
        return new BukkitSenderWrapper(commandSender);
    }

}
