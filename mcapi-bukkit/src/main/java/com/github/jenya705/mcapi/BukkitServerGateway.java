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
public class BukkitServerGateway extends AbstractApplicationModule implements Listener {

    private ServerGateway serverGateway() {
        return app().getServerGateway();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void chat(AsyncChatEvent event) {
        serverGateway()
                .receiveMessage(
                        BukkitPlayerWrapper.of(event.getPlayer()),
                        PlainTextComponentSerializer.plainText().serialize(event.message())
                );
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void join(PlayerJoinEvent event) {
        serverGateway()
                .join(BukkitPlayerWrapper.of(event.getPlayer()));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void quit(PlayerQuitEvent event) {
        serverGateway()
                .quit(BukkitPlayerWrapper.of(event.getPlayer()));
    }

    @OnStartup
    public void registerSelf() {
        Bukkit.getPluginManager().registerEvents(this, bean(BukkitApplication.class));
    }
}
