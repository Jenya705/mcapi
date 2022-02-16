package com.github.jenya705.mcapi.server.module.options;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Jenya705
 */
@RequiredArgsConstructor
public class ContainerRawOptionsMessage implements RawOptionsMessage {

    private final Map<String, List<RawOptionsElement>> options;

    @Override
    public List<RawOptionsElement> getOptions(String name) {
        return options.get(name.toLowerCase(Locale.ROOT));
    }

}
