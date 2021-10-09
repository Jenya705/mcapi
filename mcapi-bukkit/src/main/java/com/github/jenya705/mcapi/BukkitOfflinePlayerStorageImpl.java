package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.log.TimerTask;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

/**
 * @author Jenya705
 */
@Slf4j
public class BukkitOfflinePlayerStorageImpl extends AbstractJavaApplicationModule implements BukkitOfflinePlayerStorage, Listener {

    private final Map<String, UUID> nickUUIDMap = new HashMap<>();

    @Bean
    private BukkitApplication plugin;

    @SneakyThrows
    @OnInitializing(priority = 0)
    public void initialize() {
        TimerTask task = TimerTask.start(log, "Loading nick and uuids...");
        Map<String, Object> loadedNickUUIDMap = core().loadConfig("bukkit-nick-uuid");
        if (loadedNickUUIDMap != null) {
            for (Map.Entry<String, Object> entry : loadedNickUUIDMap.entrySet()) {
                if (!(entry.getValue() instanceof String)) {
                    throw new IllegalArgumentException("Loaded offline players config contains not string");
                }
                nickUUIDMap.put(entry.getKey(), UUID.fromString((String) entry.getValue()));
            }
        }
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
        task.complete();
    }

    @SneakyThrows
    @OnDisable(priority = 4)
    public void save() {
        Map<String, Object> toSave = new HashMap<>();
        nickUUIDMap
                .forEach((key, value) -> toSave.put(key, value.toString()));
        core().saveConfig("bukkit-nick-uuid", toSave);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void playerJoin(PlayerJoinEvent event) {
        nickUUIDMap.put(
                event.getPlayer().getName().toLowerCase(Locale.ROOT),
                event.getPlayer().getUniqueId()
        );
    }

    @Override
    public org.bukkit.OfflinePlayer getPlayer(String name) {
        String loweredName = name.toLowerCase(Locale.ROOT);
        if (nickUUIDMap.containsKey(loweredName)) {
            return Bukkit.getOfflinePlayer(nickUUIDMap.get(loweredName));
        }
        return null;
    }
}
