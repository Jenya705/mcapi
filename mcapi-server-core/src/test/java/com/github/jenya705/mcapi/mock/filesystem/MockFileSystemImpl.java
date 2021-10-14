package com.github.jenya705.mcapi.mock.filesystem;

import java.util.HashMap;
import java.util.Map;

public class MockFileSystemImpl implements MockFileSystem {

    private final Map<String, Map<String, Object>> configs = new HashMap<>();
    private final Map<String, byte[]> specifics = new HashMap<>();

    @Override
    public Map<String, Object> getConfig(String name) {
        return configs.getOrDefault(name, new HashMap<>());
    }

    @Override
    public byte[] getSpecific(String name) {
        return specifics.getOrDefault(name, null);
    }

    @Override
    public void createFile(String name, byte[] bytes) {
        specifics.put(name, bytes);
    }

    @Override
    public void createConfig(String name, Map<String, Object> config) {
        configs.put(name, config);
    }
}
