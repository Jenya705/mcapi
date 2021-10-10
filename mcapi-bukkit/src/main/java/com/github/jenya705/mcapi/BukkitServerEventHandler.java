package com.github.jenya705.mcapi;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author Jenya705
 */
public class BukkitServerEventHandler extends AbstractApplicationModule implements Listener {

    private ServerLocalEventHandler eventHandler() {
        return app().getServerLocalEventHandler();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void chat(AsyncChatEvent event) {
        eventHandler()
                .receiveMessage(
                        BukkitPlayerWrapper.of(event.getPlayer()),
                        PlainTextComponentSerializer.plainText().serialize(event.message())
                );
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void join(PlayerJoinEvent event) {
        eventHandler()
                .join(BukkitPlayerWrapper.of(event.getPlayer()));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void quit(PlayerQuitEvent event) {
        eventHandler()
                .quit(BukkitPlayerWrapper.of(event.getPlayer()));
    }

    @OnStartup
    public void registerSelf() {
        Bukkit.getPluginManager().registerEvents(this, bean(BukkitApplication.class));
    }
}
