package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.block.BukkitBlockDataRegistry;
import com.github.jenya705.mcapi.command.CommandExecutor;
import com.github.jenya705.mcapi.permission.PermissionManagerHook;
import com.github.jenya705.mcapi.world.World;
import lombok.Cleanup;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.PluginCommand;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginManager;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
public class BukkitServerCore extends AbstractJavaApplicationModule implements JavaServerCore {

    private static final Yaml yaml = generateYaml();

    private static Yaml generateYaml() {
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setPrettyFlow(true);
        return new Yaml(dumperOptions);
    }

    @Bean
    private BukkitApplication plugin;

    @Bean
    private PermissionManagerHook permissionManagerHook;

    @Bean
    private BukkitOfflinePlayerStorage offlinePlayerStorage;

    @Bean
    private BukkitBlockDataRegistry blockDataRegistry;

    @OnInitializing(priority = -1)
    public void initialize() {
        plugin.getDataFolder().mkdir();
        if (permissionManagerHook == null) {
            plugin.getLogger().severe(
                    "Permission manager can not be found. " +
                            "Maybe you are not using LuckPerms or did not download Vault, or did not download permission plugin for Vault. " +
                            "Because of that, bot commands can not be given to player on the linking state"
            );
        }
    }

    @Override
    public void addCommand(String name, CommandExecutor executor, String permissionName) {
        PluginCommand command = plugin.getCommand(name);
        command.setExecutor(new BukkitCommandWrapper(plugin, executor, permissionName));
        Bukkit.getOnlinePlayers().forEach(org.bukkit.entity.Player::updateCommands);
    }

    @Override
    public void permission(String name, boolean toggled) {
        List<String> subPermissions = generatePermissionList(name);
        PermissionDefault bukkitToggled = toggled ? PermissionDefault.TRUE : PermissionDefault.OP;
        PluginManager pluginManager = Bukkit.getPluginManager();
        for (int i = 0; i < subPermissions.size(); ++i) {
            Permission permission = pluginManager.getPermission(subPermissions.get(i));
            if (permission == null) {
                permission = new Permission(subPermissions.get(i), bukkitToggled);
                pluginManager.addPermission(permission);
            }
            for (int j = 0; j < i; ++j) {
                permission.addParent(subPermissions.get(j) + ".*", true);
            }
        }
    }

    @Override
    public void givePermission(Player player, boolean toggled, String... permissions) {
        if (permissionManagerHook == null) return;
        permissionManagerHook.givePermission(player, toggled, permissions);
    }

    private List<String> generatePermissionList(String name) {
        List<String> array = new ArrayList<>();
        int current = 0;
        while ((current = name.indexOf('.', current + 1)) != -1) {
            array.add(name.substring(0, current));
        }
        array.add(name);
        return array;
    }

    @Override
    public Collection<JavaPlayer> getJavaPlayers() {
        return Bukkit
                .getOnlinePlayers()
                .stream()
                .map(BukkitWrapper::player)
                .collect(Collectors.toList());
    }

    @Override
    public JavaPlayer getJavaPlayer(String name) {
        return BukkitWrapper.player(Bukkit.getPlayer(name));
    }

    @Override
    public JavaPlayer getJavaPlayer(UUID uuid) {
        return BukkitWrapper.player(Bukkit.getPlayer(uuid));
    }

    @Override
    public OfflinePlayer getOfflinePlayer(String name) {
        return BukkitWrapper.offlinePlayer(offlinePlayerStorage.getPlayer(name));
    }

    @Override
    public OfflinePlayer getOfflinePlayer(UUID uuid) {
        return BukkitWrapper.offlinePlayer(Bukkit.getOfflinePlayer(uuid));
    }

    @Override
    public World getWorld(String id) {
        return BukkitWrapper.world(Bukkit.getWorld(NamespacedKey.minecraft(id)));
    }

    @Override
    public Map<String, Object> loadConfig(String file) throws IOException {
        File fileObject = new File(plugin.getDataFolder(), file + ".yml");
        if (!fileObject.exists()) fileObject.createNewFile(); // IGNORED
        @Cleanup Reader reader = new FileReader(fileObject);
        return Objects.requireNonNullElse(yaml.load(reader), new LinkedHashMap<>());
    }

    @Override
    public byte[] loadSpecific(String file) throws IOException {
        File fileObject = new File(plugin.getDataFolder(), file);
        if (!fileObject.exists()) fileObject.createNewFile(); // IGNORED
        return Files.readAllBytes(fileObject.toPath());
    }

    @Override
    public void saveConfig(String file, Map<String, Object> config) throws IOException {
        File fileObject = new File(plugin.getDataFolder(), file + ".yml");
        if (!fileObject.exists()) fileObject.createNewFile(); // IGNORED
        @Cleanup Writer writer = new FileWriter(fileObject);
        yaml.dump(config, writer);
    }

    @Override
    public void saveSpecific(String file, byte[] bytes) throws IOException {
        File fileObject = new File(plugin.getDataFolder(), file);
        if (!fileObject.exists()) fileObject.createNewFile(); // IGNORED
        Files.write(fileObject.toPath(), bytes, StandardOpenOption.WRITE);
    }

    public File getFile(String file) {
        return new File(plugin.getDataFolder(), file);
    }

    @Override
    public boolean isExistsFile(String file) {
        return getFile(file).exists();
    }

    @Override
    public void mkdirs(String file) {
        getFile(file).mkdirs();
    }

    @Override
    public String getAbsolutePath(String file) {
        return getFile(file).getAbsolutePath();
    }

    @Override
    public void disable() {
        Bukkit.getPluginManager().disablePlugin(plugin);
    }
}
