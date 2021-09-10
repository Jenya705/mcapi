package com.github.jenya705.mcapi;

import com.destroystokyo.paper.event.server.AsyncTabCompleteEvent;
import com.github.jenya705.mcapi.command.CommandExecutor;
import com.github.jenya705.mcapi.command.CommandTab;
import com.github.jenya705.mcapi.stringful.ArrayStringfulIterator;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
public class PaperAsyncTabListener implements Listener, BaseCommon {

    private BukkitApplication plugin;

    @EventHandler
    public void tab(AsyncTabCompleteEvent event) {
        if (!event.isCommand()) {
            return;
        }
        String buffer = event.getBuffer();
        if (buffer.startsWith("/")) {
            buffer = buffer.substring(1);
        }
        String[] splitBuffer = bufferToArgs(buffer);
        PluginCommand pluginCommand = Bukkit.getPluginCommand(splitBuffer[0]);
        if (pluginCommand == null ||
                !plugin.equals(pluginCommand.getPlugin()) ||
                !(pluginCommand.getExecutor() instanceof BukkitCommandWrapper wrapper)) {
            return;
        }
        CommandExecutor executor = wrapper.getExecutor();
        List<CommandTab> tabs = executor.asyncTab(
                BukkitWrapper.sender(event.getSender()),
                new ArrayStringfulIterator(splitBuffer, 1),
                wrapper.getPermission()
        );
        tabs = tabs == null ? Collections.emptyList() : tabs;
        event.completions(
                tabs
                        .stream()
                        .map(it -> it.getTooltip() == null ?
                                AsyncTabCompleteEvent.Completion.completion(it.getName()) :
                                AsyncTabCompleteEvent.Completion.completion(
                                        it.getName(),
                                        Component.text(it.getTooltip())
                                )
                        )
                        .collect(Collectors.toList())
        );
    }

    private String[] bufferToArgs(String str) {
        List<String> list = new ArrayList<>();
        int currentIndex = 0;
        int lastIndex = 0;
        while ((currentIndex = str.indexOf(' ', currentIndex)) != -1) {
            list.add(str.substring(lastIndex, currentIndex));
            currentIndex++;
            lastIndex = currentIndex;
        }
        if (str.endsWith(" ")) list.add(" ");
        return list.toArray(String[]::new);
    }

    @OnStartup
    public void registerSelf() {
        plugin = bean(BukkitApplication.class);
        Bukkit.getPluginManager().registerEvents(this, plugin);
        plugin.setAsyncTab(true);
    }
}
