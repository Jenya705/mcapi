package com.github.jenya705.mcapi.bukkit;

import com.github.jenya705.mcapi.bukkit.wrapper.BukkitWrapper;
import com.github.jenya705.mcapi.server.command.CommandExecutor;
import com.github.jenya705.mcapi.server.command.CommandTab;
import com.github.jenya705.mcapi.server.stringful.ArrayStringfulIterator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
@Getter
@AllArgsConstructor
public class BukkitCommandWrapper implements org.bukkit.command.CommandExecutor, org.bukkit.command.TabCompleter {

    private final BukkitApplication plugin;
    private final CommandExecutor executor;
    private final String permission;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        executor.onCommand(BukkitWrapper.sender(sender), new ArrayStringfulIterator(args), permission);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (plugin.isAsyncTab()) return Collections.emptyList();
        List<CommandTab> tabs = executor.onTab(BukkitWrapper.sender(sender), new ArrayStringfulIterator(args), permission);
        return tabs == null ?
                Collections.emptyList() :
                tabs
                        .stream()
                        .map(CommandTab::getName)
                        .collect(Collectors.toList());
    }
}
