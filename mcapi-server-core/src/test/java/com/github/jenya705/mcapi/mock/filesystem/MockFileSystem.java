package com.github.jenya705.mcapi.mock.filesystem;

import java.util.Map;

public interface MockFileSystem {

    Map<String, Object> getConfig(String name);

    byte[] getSpecific(String name);

    void createFile(String name, byte[] bytes);

    void createConfig(String name, Map<String, Object> config);

}
