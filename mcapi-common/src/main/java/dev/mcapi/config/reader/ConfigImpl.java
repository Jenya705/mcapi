package dev.mcapi.config.reader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public interface ConfigImpl {

    Map<String, Object> read(InputStream is) throws IOException;

    void save(Map<String, Object> map, Map<String, Object> comments, OutputStream os) throws IOException;

}
