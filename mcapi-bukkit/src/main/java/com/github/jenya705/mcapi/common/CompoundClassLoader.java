package com.github.jenya705.mcapi.common;

import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 *
 * A compound class loader represents group of class loaders which work together
 *
 * @since 1.0
 * @author Jenya705
 */
@AllArgsConstructor
public class CompoundClassLoader extends ClassLoader{

    private final Collection<ClassLoader> classLoaders;

    public CompoundClassLoader(ClassLoader... classLoaders) {
        this(Arrays.asList(classLoaders));
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

}
