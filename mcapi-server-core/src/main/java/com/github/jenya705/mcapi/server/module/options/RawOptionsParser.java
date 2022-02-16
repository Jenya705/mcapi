package com.github.jenya705.mcapi.server.module.options;

import com.google.inject.ImplementedBy;

/**
 * @author Jenya705
 */
@ImplementedBy(RawOptionsParserImpl.class)
public interface RawOptionsParser {

    RawOptionsMessage parse(String message);

}
