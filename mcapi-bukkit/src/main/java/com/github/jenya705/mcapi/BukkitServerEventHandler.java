package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.entity.event.*;
import com.github.jenya705.mcapi.player.BukkitPlayerWrapper;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author Jenya705
 */
public class BukkitServerEventHandler extends AbstractApplicationModule implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void chat(AsyncChatEvent event) {
        eventLoop()
                .invoke(
                        new EntityMessageEvent(
                                PlainTextComponentSerializer.plainText().serialize(event.message()),
                                BukkitPlayerWrapper.of(event.getPlayer())
                        )
                );
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void join(PlayerJoinEvent event) {
        eventLoop()
                .invoke(
                        new EntityJoinEvent(
                                BukkitPlayerWrapper.of(event.getPlayer())
                        )
                );
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void quit(PlayerQuitEvent event) {
        eventLoop()
                .invoke(
                        new EntityQuitEvent(
                                BukkitPlayerWrapper.of(event.getPlayer())
                        )
                );
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void blockBreak(BlockBreakEvent event) {
        eventLoop()
                .invoke(
                        new EntityPlayerBlockBreakEvent(
                                BukkitWrapper.player(event.getPlayer()),
                                BukkitWrapper.block(event.getBlock())
                        )
                );
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void blockPlace(BlockPlaceEvent event) {
        eventLoop()
                .invoke(
                        new EntityPlayerBlockPlaceEvent(
                                BukkitWrapper.player(event.getPlayer()),
                                BukkitWrapper.block(event.getBlock())
                        )
                );
    }

    @OnStartup
    public void registerSelf() {
        Bukkit.getPluginManager().registerEvents(this, bean(BukkitApplication.class));
    }
}
