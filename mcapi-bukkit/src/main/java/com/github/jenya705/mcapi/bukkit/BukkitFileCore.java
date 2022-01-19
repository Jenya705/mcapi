package com.github.jenya705.mcapi.bukkit;

import com.google.inject.Inject;
import lombok.Cleanup;
import org.bukkit.Bukkit;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class BukkitFileCore {

    private static final Yaml yaml = generateYaml();

    private static Yaml generateYaml() {
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setPrettyFlow(true);
        return new Yaml(dumperOptions);
    }

    private final BukkitApplication plugin;

    @Inject
    public BukkitFileCore(BukkitApplication plugin) {
        this.plugin = plugin;
    }

    public Map<String, Object> loadConfig(String file) throws IOException {
        File fileObject = new File(plugin.getDataFolder(), file + ".yml");
        if (!fileObject.exists()) fileObject.createNewFile(); // IGNORED
        @Cleanup Reader reader = new FileReader(fileObject);
        return Objects.requireNonNullElse(yaml.load(reader), new LinkedHashMap<>());
    }

    public byte[] loadSpecific(String file) throws IOException {
        File fileObject = new File(plugin.getDataFolder(), file);
        if (!fileObject.exists()) fileObject.createNewFile(); // IGNORED
        return Files.readAllBytes(fileObject.toPath());
    }

    public void saveConfig(String file, Map<String, Object> config) throws IOException {
        File fileObject = new File(plugin.getDataFolder(), file + ".yml");
        if (!fileObject.exists()) fileObject.createNewFile(); // IGNORED
        @Cleanup Writer writer = new FileWriter(fileObject);
        yaml.dump(config, writer);
    }

    public void saveSpecific(String file, byte[] bytes) throws IOException {
        File fileObject = new File(plugin.getDataFolder(), file);
        if (!fileObject.exists()) fileObject.createNewFile(); // IGNORED
        Files.write(fileObject.toPath(), bytes, StandardOpenOption.WRITE);
    }

    public File getFile(String file) {
        return new File(plugin.getDataFolder(), file);
    }

    public boolean isExistsFile(String file) {
        return getFile(file).exists();
    }

    public void mkdirs(String file) {
        getFile(file).mkdirs();
    }

    public String getAbsolutePath(String file) {
        return getFile(file).getAbsolutePath();
    }

    public void disable() {
        Bukkit.getPluginManager().disablePlugin(plugin);
    }
    
}
