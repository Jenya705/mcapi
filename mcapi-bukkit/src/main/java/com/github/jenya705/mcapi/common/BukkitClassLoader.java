package com.github.jenya705.mcapi.common;

import lombok.AllArgsConstructor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 * @author Jenya705
 */
@AllArgsConstructor
public class BukkitClassLoader extends ClassLoader {

    private final JavaPlugin plugin;
    private final Collection<ClassLoader> classLoaders;

    public BukkitClassLoader(JavaPlugin plugin, ClassLoader... classLoaders) {
        this(plugin, Arrays.asList(classLoaders));
    }

    @Override
    public URL getResource(String name) {
        for (ClassLoader loader : classLoaders) {
            if (loader != null) {
                URL resource = loader.getResource(name);
                if (resource != null) {
                    return resource;
                }
            }
        }
        return null;
    }

    @Override
    public InputStream getResourceAsStream(String name) {
        InputStream pluginIs = plugin.getResource(name);
        if (pluginIs != null) return pluginIs;
        for (ClassLoader loader : classLoaders) {
            if (loader != null) {
                InputStream is = loader.getResourceAsStream(name);
                if (is != null) {
                    return is;
                }
            }
        }
        return null;
    }

    @Override
    public Enumeration<URL> getResources(String name) throws IOException {
        List<URL> urls = new ArrayList<>();
        for (ClassLoader loader : classLoaders) {
            if (loader != null) {
                try {
                    Enumeration<URL> resources = loader.getResources(name);
                    while (resources.hasMoreElements()) {
                        URL resource = resources.nextElement();
                        if (resource != null && !urls.contains(resource)) {
                            urls.add(resource);
                        }
                    }
                } catch (IOException ignored) {}
            }
        }
        return Collections.enumeration(urls);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        for (ClassLoader loader : classLoaders) {
            if (loader != null) {
                try {
                    return loader.loadClass(name);
                } catch (ClassNotFoundException ignored) {}
            }
        }
        throw new ClassNotFoundException();
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        return loadClass(name);
    }

    @Override
    public String toString() {
        return String.format("CompoundClassloader %s", classLoaders);
    }
}
