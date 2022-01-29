package com.github.jenya705.mcapi.bukkit;

import com.google.inject.Inject;
import lombok.Cleanup;
import org.bukkit.Bukkit;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

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
        File fileObject = getFile(file + ".yml");
        fileObject.getParentFile().mkdirs();
        if (!fileObject.exists()) {
            fileObject.createNewFile(); // IGNORED
            return new LinkedHashMap<>();
        }
        @Cleanup Reader reader = new FileReader(fileObject);
        return Objects.requireNonNullElse(yaml.load(reader), new LinkedHashMap<>());
    }

    public byte[] loadSpecific(String file) throws IOException {
        File fileObject = getFile(file);
        fileObject.getParentFile().mkdirs();
        if (!fileObject.exists()) {
            fileObject.createNewFile(); // IGNORED
            return new byte[0];
        }
        return Files.readAllBytes(fileObject.toPath());
    }

    public InputStream loadSpecificStream(String file) throws IOException {
        File fileObject = getFile(file);
        return new FileInputStream(fileObject);
    }

    public void saveConfig(String file, Map<String, Object> config) throws IOException {
        File fileObject = getFile(file + ".yml");
        fileObject.getParentFile().mkdirs();
        if (!fileObject.exists()) fileObject.createNewFile(); // IGNORED
        @Cleanup Writer writer = new FileWriter(fileObject);
        yaml.dump(config, writer);
    }

    public void saveSpecific(String file, byte[] bytes) throws IOException {
        File fileObject = getFile(file);
        fileObject.getParentFile().mkdirs();
        if (!fileObject.exists()) fileObject.createNewFile(); // IGNORED
        Files.write(fileObject.toPath(), bytes, StandardOpenOption.WRITE);
    }

    public File getFile(String file) {
        return new File(plugin.getDataFolder(), file);
    }

    public Collection<String> getFilesInDirectory(String directory) {
        File file = getFile(directory);
        File[] listFiles = file.listFiles();
        if (file.isDirectory() && listFiles != null) {
            return Arrays
                    .stream(listFiles)
                    .map(File::getName)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
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
