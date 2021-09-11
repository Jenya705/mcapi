package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.command.CommandExecutor;
import com.github.jenya705.mcapi.permission.PermissionManagerHook;
import lombok.Cleanup;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
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
public class BukkitServerCore implements JavaServerCore, JavaBaseCommon {

    private static final Yaml yaml = generateYaml();

    private static Yaml generateYaml() {
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setPrettyFlow(true);
        return new Yaml(dumperOptions);
    }

    private JavaPlugin plugin;
    private PermissionManagerHook permissionManagerHook;

    @OnInitializing(priority = 0)
    public void initialize() {
        plugin = bean(BukkitApplication.class);
        plugin.getDataFolder().mkdir();
        permissionManagerHook = bean(PermissionManagerHook.class);
        if (permissionManagerHook == null) {
            plugin.getLogger().severe(
                    "Permission manager not found. " +
                            "Maybe you are not using LuckPerms or did not download Vault. " +
                            "Because of that, bot commands can not be given to player on the linking state"
            );
        }
    }

    @Override
    public void addCommand(String name, CommandExecutor executor, String permissionName) {
        PluginCommand command = plugin.getCommand(name);
        if (command == null) return;
        command.setExecutor(new BukkitCommandWrapper(executor, permissionName));
        Bukkit.getOnlinePlayers().forEach(Player::updateCommands);
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
    public void givePermission(ApiPlayer player, boolean toggled, String... permissions) {
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
    // TODO Change method to get offline player
    public ApiOfflinePlayer getOfflinePlayer(String name) {
        return BukkitWrapper.offlinePlayer(Bukkit.getOfflinePlayer(name));
    }

    @Override
    public ApiOfflinePlayer getOfflinePlayer(UUID uuid) {
        return BukkitWrapper.offlinePlayer(Bukkit.getOfflinePlayer(uuid));
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

    @Override
    public File getPluginFile(String file) {
        return new File(plugin.getDataFolder(), file);
    }
}
