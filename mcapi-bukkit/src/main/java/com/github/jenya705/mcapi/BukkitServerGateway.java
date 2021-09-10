package com.github.jenya705.mcapi;

import com.destroystokyo.paper.event.server.AsyncTabCompleteEvent;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.Arrays;

/**
 * @author Jenya705
 */
public class BukkitServerGateway implements BaseCommon, Listener {

    private ServerGateway gateway() {
        return app().getServerGateway();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void chat(AsyncChatEvent event) {
        gateway()
                .receiveMessage(
                        BukkitPlayerWrapper.of(event.getPlayer()),
                        PlainTextComponentSerializer.plainText().serialize(event.message())
                );
    }

    @OnStartup
    public void registerSelf() {
        Bukkit.getPluginManager().registerEvents(this, bean(BukkitApplication.class));
    }
}
