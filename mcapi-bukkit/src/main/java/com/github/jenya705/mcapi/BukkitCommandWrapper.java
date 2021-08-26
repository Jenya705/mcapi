package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.command.CommandExecutor;
import com.github.jenya705.mcapi.stringful.ArrayStringfulIterator;
import lombok.AllArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class BukkitCommandWrapper implements org.bukkit.command.CommandExecutor, org.bukkit.command.TabExecutor {

    private final CommandExecutor executor;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        executor.onCommand(BukkitCommandSenderWrapper.of(sender), new ArrayStringfulIterator(args));
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return executor.onTab(BukkitCommandSenderWrapper.of(sender), new ArrayStringfulIterator(args));
    }
}
