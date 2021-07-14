package com.github.jenya705.mcapi.common;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 * @author Jenya705
 */
public class CompoundClassLoader extends ClassLoader {

    private final Collection<ClassLoader> loaders;

    /**
     * Constructs a new CompoundClassLoader.
     * @param loaders the loaders to iterate over
     */
    public CompoundClassLoader(ClassLoader... loaders) {
        this.loaders = Arrays.asList(loaders);
    }

    /**
     * Constructs a new CompoundClassLoader.
     * @param loaders the loaders to iterate over
     */
    public CompoundClassLoader(Collection<ClassLoader> loaders) {
        this.loaders = loaders;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public URL getResource(String name) {
        for (ClassLoader loader : loaders) {
            if (loader != null) {
                URL resource = loader.getResource(name);
                if (resource != null) {
                    return resource;
                }
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InputStream getResourceAsStream(String name) {
        for (ClassLoader loader : loaders) {
            if (loader != null) {
                InputStream is = loader.getResourceAsStream(name);
                if (is != null) {
                    return is;
                }
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Enumeration<URL> getResources(String name) throws IOException {
        List<URL> urls = new ArrayList<URL>();
        for (ClassLoader loader : loaders) {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        for (ClassLoader loader : loaders) {
            if (loader != null) {
                try {
                    return loader.loadClass(name);
                } catch (ClassNotFoundException cnfe) {
                    // ignoring, but to keep checkstyle happy ("Must have at least one statement."):
                    cnfe.getMessage();
                }
            }
        }
        throw new ClassNotFoundException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        // loader.loadClass(name, resolve) is not visible!
        return loadClass(name);
    }

    @Override
    public String toString() {
        return String.format("CompoundClassloader %s", loaders);
    }
}
