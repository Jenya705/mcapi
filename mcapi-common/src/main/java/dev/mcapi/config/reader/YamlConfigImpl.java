package dev.mcapi.config.reader;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class YamlConfigImpl implements ConfigImpl {

    private static final Yaml YAML = new Yaml();

    @Override
    public Map<String, Object> read(InputStream is) {
        return Objects.requireNonNullElse(YAML.load(is), Collections.emptyMap());
    }

    @Override
    public void save(Map<String, Object> map, Map<String, Object> comments, OutputStream os) {
        YAML.dump(map, new OutputStreamWriter(os));
    }

}
